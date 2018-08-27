package com.deuce.me.matura.util;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.deuce.me.matura.models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ingli on 22.08.2018.
 */

public class SchoolMapper {

    private Spinner schoolSpinner, gradeSpinner;
    Map<String, Integer> mMap;
    private Context mContext;

    public SchoolMapper(Context mContext, Spinner schoolSpinner, Spinner gradeSpinner) {
        this.mContext = mContext;
        this.schoolSpinner = schoolSpinner;
        this.gradeSpinner = gradeSpinner;
    }

    public Map<String, Integer> map(List<String> mData) {
        Map<String, Integer> mOut = new LinkedHashMap<>();

        for(int n = 0; n < mData.size(); n++) {
            String[] tempData = mData.get(n).split(":");
            mOut.put(tempData[0], Integer.parseInt(tempData[1]));
        }

        return mOut;
    }


    public void startDisplay(String path){

        MyReader mReader = new MyReader(mContext);

        mMap = map(mReader.read(path));

        String[] keySet = mMap.keySet().toArray(new String[mMap.size()]);

        ArrayList<String> mSchools = new ArrayList<>();

        for(int n = 0; n < mMap.size(); n++) {
            mSchools.add(keySet[n]);
        }

        ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSchools);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        schoolSpinner.setAdapter(schoolAdapter);
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int mClasses = mMap.get(adapterView.getAdapter().getItem(i));

                    ArrayList<Integer> mGrades = new ArrayList<>();

                    for(int n = 1; n <= mClasses; n++) {
                        mGrades.add(n);
                    }

                ArrayAdapter<Integer> gradeAdapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_item, mGrades);
                gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                gradeSpinner.setAdapter(gradeAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gradeSpinner.clearFocus();
                gradeSpinner.setAdapter(null);

            }
        });
    }

    public void startDisplay(String path, UserModel mModel){

        MyReader mReader = new MyReader(mContext);

        mMap = map(mReader.read(path));

        String[] keySet = mMap.keySet().toArray(new String[mMap.size()]);

        ArrayList<String> mSchools = new ArrayList<>();

        mSchools.add(mModel.getSchool());
        System.out.println("model school: " + mModel.getSchool());

        for(int n = 1; n < mMap.size(); n++) {
            System.out.println("School keySet: " + keySet[n]);
            if(!keySet[n].equals(mModel.getSchool())) {
                mSchools.add(keySet[n]);
            }
        }

        ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSchools);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int mGrades = mMap.get(mModel.getSchool());
        ArrayList<Integer> mGradesList = new ArrayList<>();

        mGradesList.add(mModel.getGrade());

        for(int n = 1; n <= mGrades; n++) {
            if(n != mModel.getGrade()){
                mGradesList.add(n);
            }
        }

        ArrayAdapter<Integer> gradeAdapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_item, mGrades);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        gradeSpinner.setAdapter(gradeAdapter);

        schoolSpinner.setAdapter(schoolAdapter);
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int mGrades = mMap.get(adapterView.getAdapter().getItem(i));

                ArrayList<Integer> mGradesList = new ArrayList<>();

                for(int n = 1; n <= mGrades; n++) {
                    mGradesList.add(n);
                }

                ArrayAdapter<Integer> gradeAdapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_item, mGradesList);
                gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                gradeSpinner.setAdapter(gradeAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gradeSpinner.clearFocus();
                gradeSpinner.setAdapter(null);

            }
        });

    }
}
