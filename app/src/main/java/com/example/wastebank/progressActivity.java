package com.example.wastebank;

/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class progressActivity extends AppCompatActivity {

BarChart barChart ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
             barChart = (BarChart) findViewById(R.id.barchart);



        /*BarChart chart = findViewById(R.id.barchart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(1645f, 7));
        NoOfEmp.add(new BarEntry(1578f, 8));
        NoOfEmp.add(new BarEntry(1695f, 9));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData((IBarDataSet) year, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);

*/
        BarDataSet barDataSeet1 =new BarDataSet(dataValues1(),"Data set 1");
        BarData barData = new BarData();
        barData.addDataSet(barDataSeet1);
        barChart.setData(barData);
        barChart.invalidate();
    }
    private  ArrayList<BarEntry> dataValues1(){
        ArrayList<BarEntry> dataVals=new ArrayList<>();
        dataVals.add(new BarEntry(2,3));
        dataVals.add(new BarEntry(4,4));
        dataVals.add(new BarEntry(6,6));
        dataVals.add(new BarEntry(8,11));
        dataVals.add(new BarEntry(10,11));
        return dataVals;


    }




}