package foureg.eg.travelsearchform.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import foureg.eg.travelsearchform.R;
import foureg.eg.travelsearchform.common.Constants;
import foureg.eg.travelsearchform.data.models.Place;
import foureg.eg.travelsearchform.ui.controllers.SearchCtrl;


public class SearchAc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
    }

    /**
     * init views
     */
    private void initViews()
    {
        // Obtain views
        final AutoCompleteTextView fromEditor = (AutoCompleteTextView)findViewById(R.id.activity_search_from_editor);
        final AutoCompleteTextView toEditor = (AutoCompleteTextView)findViewById(R.id.activity_search_to_editor);
        EditText dateEditor = (EditText) findViewById(R.id.activity_search_travel_date_editor);
        Button searchBtn = (Button) findViewById(R.id.activity_search_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchAc.this, getString(R.string.txt_search_msg), Toast.LENGTH_LONG).show();
            }
        });

        // Get locations ordered to current location
        mCtrl.getLocations(this, new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case Constants.Error_Service_Disabled:
                    {
                        Toast.makeText(SearchAc.this, getString(R.string.txt_error_gps_disabled), Toast.LENGTH_LONG).show();
                        break;
                    }

                    case Constants.Error_Invalid_JSON:
                    {
                        Toast.makeText(SearchAc.this, getString(R.string.txt_error_invalid_data), Toast.LENGTH_LONG).show();
                        break;
                    }

                    case Constants.Service_Enabled:
                    {
                        List<Place> places = msg.getData().getParcelableArrayList(Constants.Bundle_Locations_Key);
                        String[] items = new String[places.size()];
                        int i = 0;
                        for(Place place : places)
                        {
                            items[i++] = place.getName();
                            ArrayAdapter<String> locationsAdapter = new ArrayAdapter<>(SearchAc.this, android.R.layout.simple_dropdown_item_1line, items);
                            fromEditor.setAdapter(locationsAdapter);
                            toEditor.setAdapter(locationsAdapter);
                        }

                        break;
                    }
                }
            }
        });

        // Listen to clicks on editor
        dateEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Show calendar dialog

                    DatePickerDialog datePicker = new DatePickerDialog(SearchAc.this,
                            R.style.AppTheme, datePickerListener,
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH));
                    datePicker.setCancelable(false);
                    datePicker.setTitle(getString(R.string.txt_travel_date_caption));

                    mCalendarDlg = new DatePickerDialog(SearchAc.this, datePickerListener, mCalendar
                            .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH));
                    mCalendarDlg.show();


            }

        });
    }

    private DatePickerDialog mCalendarDlg = null;

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year = String.valueOf(selectedYear);
            String month = String.valueOf(selectedMonth + 1);
            String day = String.valueOf(selectedDay);

            EditText dateEditor = (EditText) findViewById(R.id.activity_search_travel_date_editor);
            dateEditor.setText("" + day + "/" + month + "/" + year);

        }
    };

    // Hold the search controller
    private SearchCtrl mCtrl = new SearchCtrl();

    private Calendar mCalendar = Calendar.getInstance(TimeZone.getDefault());


}
