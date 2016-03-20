package com.ibm.bluebridge;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ibm.bluebridge.valueobject.Position;

import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.List;

public class ManageEventsTabbedActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static Context ctxt;
    private static ManageEventsTabbedActivity fragmentActivity;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events_tabbed);
        this.ctxt = this;
        this.fragmentActivity = this;


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_events_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Activity Information";
                case 1:
                    return "Volunteers Required";
                case 2:
                    return "Contact Person";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            int tabNum = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = null;

            switch (tabNum) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_manage_events_info, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_manage_events_volunteers, container, false);
                    Button addPositionButton = (Button) rootView.findViewById(R.id.add_position);
                    addPositionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ctxt);
                            dialog.setTitle(R.string.position_dialog_title);
                            dialog.setContentView(R.layout.repeat_position_container);

                            Spinner spinner = (Spinner)dialog.findViewById(R.id.skills_spinner);
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctxt,
                                    R.array.skills_array, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);

                            final ListView listView = (ListView)dialog.findViewById(R.id.positions_listview);

                            Button addPosButton = (Button)dialog.findViewById(R.id.add_position);
                            addPosButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Position position = new Position();
                                    EditText posName = (EditText)dialog.findViewById(R.id.pos_name);
                                    Spinner skill = (Spinner)dialog.findViewById(R.id.skills_spinner);
                                    EditText numVacanies = (EditText)dialog.findViewById(R.id.number_vacancies);

                                    position.setPosName(posName.getText().toString());
                                    position.setSkills(skill.getSelectedItem().toString());
                                    position.setVacancies(Integer.parseInt(numVacanies.getText().toString()));

                                    if(listView.getAdapter()!=null) {
                                        PositionListViewAdapter listViewAdapter = (PositionListViewAdapter) listView.getAdapter();
                                        listViewAdapter.add(position);
                                        listViewAdapter.notifyDataSetChanged();
                                    } else {
                                        List<Position> positionList = new ArrayList<>();
                                        positionList.add(position);
                                        listView.setAdapter(new PositionListViewAdapter<Position>(ctxt, positionList));
                                    }
                                }
                            });


                            Button okButton = (Button)dialog.findViewById(R.id.ok);
                            okButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                            dialog.getWindow().setAttributes(lp);
                            dialog.show();
                        }
                    });
                    //final LinearLayout vacancyLayout = (LinearLayout) rootView.findViewById(R.id.vacancy_area_layout);




                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_manage_events_info, container, false);
                    break;
            }

            return rootView;
        }
    }


    private static EditText constructEditText(String id, int count) {
        EditText editText = new EditText(ctxt);
       // editText.setTag(id + "" + count);
        return editText;
    }

    private static Spinner constructSpinner(String id, int count) {
        Spinner spinner = new Spinner(ctxt);



        return spinner;
    }

    private static Button constructPositionButton() {
        Button button = new Button(ctxt);
        button.setText("Add");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int childCount = 0;
                LinearLayout parentLayout = new LinearLayout(ctxt);
                parentLayout.setOrientation(LinearLayout.HORIZONTAL);
                parentLayout.addView(constructEditText("pos_name", childCount));
                parentLayout.addView(constructSpinner("skills", childCount));
                parentLayout.addView(constructEditText("vacancy", childCount));
                v.add(parentLayout);*/
            }
        });
        return button;
    }




    private static class PositionListViewAdapter<T> extends ArrayAdapter<Position>  {

        private class ViewHolder {
            private TextView posNameView;
            private TextView skillsView;
            private TextView vacanciesView;
        }

        public PositionListViewAdapter(Context context,
                                     List<Position> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(this.getContext())
                        .inflate(R.layout.position_listitem_layout, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.posNameView = (TextView) convertView.findViewById(R.id.pos_name);
                viewHolder.skillsView = (TextView) convertView.findViewById(R.id.skills);
                viewHolder.vacanciesView = (TextView) convertView.findViewById(R.id.num_vacancies);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final Position item = getItem(position);
            if (item!= null) {
                // My layout has only one TextView
                // do whatever you want with your string and long
                viewHolder.posNameView.setText(String.format("%s", item.getPosName())+ctxt.getString(R.string.tab));
                viewHolder.skillsView.setText(String.format("%s", item.getSkills())+ctxt.getString(R.string.tab));
                viewHolder.vacanciesView.setText(String.format("%s", item.getVacancies())+ctxt.getString(R.string.tab));

            }

            return convertView;

        }



    }

}