package com.zub.covid_19;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.zub.covid_19.api.regulerData.RegulerData;//note
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zub.covid_19.api.ProvincesVietNam.Province;
import com.zub.covid_19.api.ProvincesVietNam.ProvinceVN;
import com.zub.covid_19.api.TotalVietNam.DataTotalVietNam;
import com.zub.covid_19.api.regulerData.RegulerDataVN;
import com.zub.covid_19.api.regulerData.TrendlineVnCase;
import com.zub.covid_19.api.specData.SpecData;
import com.zub.covid_19.api.specData.SpecDataVN;
import com.zub.covid_19.api.specData.VnPatientCase;
import com.zub.covid_19.util.LoadLocale;
import com.zub.covid_19.vm.ProvDataVNViewModel;
import com.zub.covid_19.vm.RegulerDataViewModel;
import com.zub.covid_19.vm.SpecDataViewModel;
import com.zub.covid_19.vm.SpecDataViewModelVN;
import com.zub.covid_19.vm.TotalVietNamViewModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class StatsFragment extends Fragment {

    private LoadLocale loadLocale;

    @BindView(R.id.stat_kasus_aktif) TextView mStatPositiveCases;
    @BindView(R.id.stat_kasus_meninggal) TextView mStatDeathCases;
    @BindView(R.id.stat_kasus_sumbuh) TextView mStatCuredCases;
  //  @BindView(R.id.stat_kasus_odp) TextView mStatMonitoringCases;
    //@BindView(R.id.stat_kasus_pdp) TextView mStatPatientCases;
    @BindView(R.id.stat_added_pos) TextView mStatAddedPositive;
    @BindView(R.id.stat_added_men) TextView mStatAddedDeath;
    @BindView(R.id.stat_added_sem) TextView mStatAddedCured;
    @BindView(R.id.stat_updated_date) TextView mUpdatedDate;
    @BindView(R.id.stat_box_shimmer) ShimmerFrameLayout mBoxShimmer;
    @BindView(R.id.stat_shimmer_cumulative_case_graph) ShimmerFrameLayout mCumulativeGraphShimmer;
    @BindView(R.id.stat_shimmer_new_case_graph) ShimmerFrameLayout mNewCaseGraphShimmer;
    @BindView(R.id.stat_box_layout) TableLayout mBoxLayout;
    @BindView(R.id.stat_cumulative_case_graph) LineChart mCumulativeCaseGraph;
  //  @BindView(R.id.stat_new_case_graph) LineChart mNewCaseGraph;

    @BindView(R.id.stat_condition_update) TextView mConditionUpdate;
    @BindView(R.id.stat_symptom_update) TextView mSymptomUpdate;
    @BindView(R.id.stat_age_update) TextView mAgeUpdate;
    @BindView(R.id.stat_sex_update) TextView mSexUpdate;
    @BindView(R.id.stat_shimmer_condition_graph) ShimmerFrameLayout mConditionGraphShimmer;
    @BindView(R.id.stat_shimmer_age_graph) ShimmerFrameLayout mAgeGraphShimmer;
    @BindView(R.id.stat_shimmer_sex_graph) ShimmerFrameLayout mSexGraphShimmer;
    @BindView(R.id.stat_shimmer_symptom_graph) ShimmerFrameLayout mSymptomGraphShimmer;
    @BindView(R.id.stat_condition_graph) BarChart mConditionGraph;
    @BindView(R.id.stat_age_graph) BarChart mAgeGraph;
    @BindView(R.id.stat_sex_graph) BarChart mSexGraph;
    @BindView(R.id.stat_symptom_graph) BarChart mSymptomGraph;

    String lastDate="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, view);
        loadLocale = new LoadLocale(getActivity());



        ProvDataVNViewModel provDataViewModel;

        provDataViewModel = ViewModelProviders.of(this).get(ProvDataVNViewModel.class);
        provDataViewModel.init();



        provDataViewModel.getRegulerData().observe(this, new Observer<ProvinceVN>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onChanged(ProvinceVN provinceVN) {
                String date= provinceVN.getData().getProvinces().get(0).getLastUpdate().toString();



                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                long milliSeconds= Long.parseLong(date);
                System.out.println(milliSeconds);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(milliSeconds);
                lastDate = formatter.format(calendar.getTime());




            }
        });


        // ========= Total Data VietNam FETCHING
        TotalVietNamViewModel totalVietNamViewModel;
        totalVietNamViewModel = ViewModelProviders.of(this).get(TotalVietNamViewModel.class);
        totalVietNamViewModel.init();
        totalVietNamViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        totalVietNamViewModel.getTotalVietNam().observe(this, new Observer<DataTotalVietNam>() {
            @Override
            public void onChanged(DataTotalVietNam dataTotalVietNam) {
                ShowTotalVietNam(dataTotalVietNam);
            }

        });



        // ========= REGULAR DATA FETCHING

        RegulerDataViewModel regulerDataViewModel;

        regulerDataViewModel = ViewModelProviders.of(this).get(RegulerDataViewModel.class);
        regulerDataViewModel.init();

        regulerDataViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showRegularDataLoading();
                } else {
                    hideRegularDataLoading();
                }
            }
        });

        regulerDataViewModel.getRegulerData().observe(this, new Observer<RegulerDataVN>() {
            @Override
            public void onChanged(RegulerDataVN regulerData) {
                //showRegulerData(regulerData);
                showCumulativeCaseGraph(regulerData);
                //showNewCaseGraph(regulerData);
            }

        });

        // ========= SPECIFIC DATA FETCHING

        SpecDataViewModelVN specDataViewModelVN;
        specDataViewModelVN = ViewModelProviders.of(this).get(SpecDataViewModelVN.class);
        specDataViewModelVN.init();
        specDataViewModelVN.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    //showSpecDataLoading();
                    mAgeGraph.setVisibility(View.GONE);
                } else {
                    //hideSpecDataLoading();
                    mAgeGraph.setVisibility(View.VISIBLE);
                }
            }
        });
        specDataViewModelVN.getSpecData().observe(this, new Observer<SpecDataVN>() {
            @Override
            public void onChanged(SpecDataVN specDataVN) {
                showAgeGraphVN(specDataVN);
                showSexGraphVN(specDataVN);
                //showUpdatedDateVN(specDataVN);
            }
        });

        SpecDataViewModel specDataViewModel;

        specDataViewModel = ViewModelProviders.of(this).get(SpecDataViewModel.class);
        specDataViewModel.init();

        specDataViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showSpecDataLoading();
                } else {
                    hideSpecDataLoading();
                }
            }
        });

        specDataViewModel.getSpecData().observe(this, new Observer<SpecData>() {
            @Override
            public void onChanged(SpecData specData) {
                showConditionGraph(specData);
                showSymptomGraph(specData);
                //showAgeGraph(specData);
                //showSexGraph(specData);
                showUpdatedDate(specData);//Show text dưới các box
            }
        });

        return view;
    }

    /*
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void showUpdatedDateVN(SpecDataVN specData) {

        String template;

        if (loadLocale.getLocale().equals("en")) {
            template = "Data collected: ";
        } else {
            template = "Data dihimpun: ";
        }


        String date = specData.getmUpdatedDate();

        int amountCond = specData.getmKasus().getmKondisiPenyerta().getmTotalData();
        double percCondition = 100.0 - specData.getmKasus().getmKondisiPenyerta().getmMissingData();

        int amountSymp = specData.getmKasus().getmGejala().getmTotalData();
        double percSymp = 100.0 - specData.getmKasus().getmGejala().getmMissingData();

        int amountAge = specData.getmKasus().getmKelompokUmur().getmTotalData();
        double percAge = 100.0 - specData.getmKasus().getmKelompokUmur().getmMissingData();

        int amountSex = specData.getmKasus().getmJenisKelamin().getmTotalData();
        double percSex = 100.0 - specData.getmKasus().getmJenisKelamin().getmMissingData();

        if (loadLocale.getLocale().equals("en")) {
            mConditionUpdate.setText(template + numberSeparator(amountCond) + " (" +
                    String.format("%.1f", percCondition) + "%) on " + date + isUsable(percCondition));
            mSymptomUpdate.setText(template + numberSeparator(amountSymp) + " (" +
                    String.format("%.1f", percSymp) + "%) on " + date + isUsable(percSymp));
            mAgeUpdate.setText(template + numberSeparator(amountAge) + " (" +
                    String.format("%.1f", percAge) + "%) on " + date + isUsable(percAge));
            mSexUpdate.setText(template + numberSeparator(amountSex) + " (" +
                    String.format("%.1f", percSex) + "%) on " + date + isUsable(percSex));
        } else {
            mConditionUpdate.setText(template + numberSeparator(amountCond) + " (" +
                    String.format("%.1f", percCondition) + "%) pada " + date + isUsable(percCondition));
            mSymptomUpdate.setText(template + numberSeparator(amountSymp) + " (" +
                    String.format("%.1f", percSymp) + "%) pada " + date + isUsable(percSymp));
            mAgeUpdate.setText(template + numberSeparator(amountAge) + " (" +
                    String.format("%.1f", percAge) + "%) pada " + date + isUsable(percAge));
            mSexUpdate.setText(template + numberSeparator(amountSex) + " (" +
                    String.format("%.1f", percSex) + "%) pada " + date + isUsable(percSex));
        }

    }
    */
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void showUpdatedDate(SpecData specData) {

        String template;

        if (loadLocale.getLocale().equals("en")) {
            template = "Data collected: ";
        } else {
            template = "Data dihimpun: ";
        }


        String date = specData.getmUpdatedDate();

        int amountCond = specData.getmKasus().getmKondisiPenyerta().getmTotalData();
        double percCondition = 100.0 - specData.getmKasus().getmKondisiPenyerta().getmMissingData();

        int amountSymp = specData.getmKasus().getmGejala().getmTotalData();
        double percSymp = 100.0 - specData.getmKasus().getmGejala().getmMissingData();

        int amountAge = specData.getmKasus().getmKelompokUmur().getmTotalData();
        double percAge = 100.0 - specData.getmKasus().getmKelompokUmur().getmMissingData();

        int amountSex = specData.getmKasus().getmJenisKelamin().getmTotalData();
        double percSex = 100.0 - specData.getmKasus().getmJenisKelamin().getmMissingData();

        if (loadLocale.getLocale().equals("en")) {
            mConditionUpdate.setText(template + numberSeparator(amountCond) + " (" +
                    String.format("%.1f", percCondition) + "%) on " + date + isUsable(percCondition));
            mSymptomUpdate.setText(template + numberSeparator(amountSymp) + " (" +
                    String.format("%.1f", percSymp) + "%) on " + date + isUsable(percSymp));
            mAgeUpdate.setText(template +" " + lastDate );
            mSexUpdate.setText(template +" "+lastDate);
        } else {
            mConditionUpdate.setText(template + numberSeparator(amountCond) + " (" +
                    String.format("%.1f", percCondition) + "%) pada " + date + isUsable(percCondition));
            mSymptomUpdate.setText(template + numberSeparator(amountSymp) + " (" +
                    String.format("%.1f", percSymp) + "%) pada " + date + isUsable(percSymp));
            mAgeUpdate.setText(template + numberSeparator(amountAge) + " (" +
                    String.format("%.1f", percAge) + "%) pada " + date + isUsable(percAge));
            mSexUpdate.setText(template + numberSeparator(amountSex) + " (" +
                    String.format("%.1f", percSex) + "%) pada " + date + isUsable(percSex));
        }

    }

    private void showSymptomGraph(SpecData specData) {

        List<SpecData.DetailedData.DerivativeDetailedData.DetailedSpecList> detailedSpecLists =
                specData.getmKasus().getmGejala().getmDetailedSpecLists();

        List<IBarDataSet> iBarDataSets = new ArrayList<>();

        for (int i = 0; i < detailedSpecLists.size(); i++) {
            ArrayList<BarEntry> key = new ArrayList<>();
            key.add(new BarEntry(i, (float) detailedSpecLists.get(i).getValue()));
            // Manipulating first letter to be capitalized
            String theDataSet = detailedSpecLists.get(i).getKey();
            if (loadLocale.getLocale().equals("en")) {
                theDataSet = translatedSymptom(theDataSet);
            }
            BarDataSet barDataSet = new BarDataSet(key, theDataSet.substring(0, 1).toUpperCase() + theDataSet.substring(1).toLowerCase());
            barDataSet.setColor(COLOR_SCHEME[i]);
            iBarDataSets.add(barDataSet);
        }
        BarData barData = new BarData(iBarDataSets);
        barData.setValueFormatter(new ValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value) + "%";
            }
        });

        barData.setValueTextSize(10);
        mSymptomGraph.setData(barData);
        mSymptomGraph.setMinimumHeight(180);
        mSymptomGraph.getXAxis().setDrawLabels(false);
        mSymptomGraph.getXAxis().setDrawAxisLine(false);
        mSymptomGraph.getXAxis().setDrawGridLines(false);
        mSymptomGraph.getAxisRight().setEnabled(false);
        mSymptomGraph.getLegend().setWordWrapEnabled(true);
        mSymptomGraph.getLegend().setTextSize(10);
        mSymptomGraph.getDescription().setEnabled(false);
        mSymptomGraph.setDoubleTapToZoomEnabled(false);
        mSymptomGraph.setPinchZoom(false);
        mSymptomGraph.animateXY(1000, 1000);
        mSymptomGraph.invalidate();

    }

    private void showSexGraphVN(SpecDataVN specData) {

        List<VnPatientCase> vnList = specData.getData().getVnPatientCases();

        int total_khoi = 0, total_dang = 0, total_chet = 0;
        int total_khoi_nam = 0, total_dang_nam = 0, total_chet_nam = 0;
        for (VnPatientCase row : vnList) {
            String status = row.getStatus();
            String gender = row.getGender();
            if(new String("Đang điều trị").equals(status)){
                total_dang++;
                if(new String("Nam").equals(gender)){
                    total_dang_nam++;
                }
            }
            if(new String("Khỏi").equals(status)){
                total_khoi++;
                if(new String("Nam").equals(gender)){
                    total_khoi_nam++;
                }
            }
            if(new String("Tử vong").equals(status)){
                total_chet++;
                if(new String("Nam").equals(gender)){
                    total_chet_nam++;
                }
            }
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        float nam_khoi_per = (float) total_khoi_nam/total_khoi*100;
        float nu_khoi_per = 100-nam_khoi_per;
        float nam_dang_per = (float) total_dang_nam/total_dang*100;
        float nu_dang_per = 100-nam_dang_per;
        float nam_chet_per = (float) total_chet_nam/total_chet*100;
        float nu_chet_per = 100-nam_chet_per;
        barEntries.add(new BarEntry(0.5f, new float[] {nam_khoi_per, nu_khoi_per}));
        barEntries.add(new BarEntry(1.5f, new float[] {nam_dang_per, nu_dang_per}));
        barEntries.add(new BarEntry(2.5f, new float[] {nam_chet_per, nu_chet_per}));

        BarDataSet barDataSet = new BarDataSet(barEntries,"");

        barDataSet.setValueTextSize(12);

        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value) + "%";
            }
        });

        barDataSet.setColors(COLOR_SCHEME[3], COLOR_SCHEME[0]);

        String txt_confirmed, txt_recovered, txt_deaths, men, woman;

        if (loadLocale.getLocale().equals("en")) {
            txt_confirmed = "Đang điều trị";
            txt_recovered = "Khỏi";
            txt_deaths = "Tử vong";
            men = "Nam";
            woman = "Nữ";
        } else {
            txt_confirmed = "Đang điều trị";
            txt_recovered = "Khỏi";
            txt_deaths = "Tử vong";
            men = "Nam";
            woman = "Nữ";
        }

        String[] dataSet = {txt_confirmed, txt_recovered, txt_deaths};

        barDataSet.setStackLabels(new String[]{men, woman});

        BarData barData = new BarData(barDataSet);

        mSexGraph.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int formated = (int) Math.round(value);
                if(formated < 0 || formated > 2) {
                    formated = 0;
                }
                return dataSet[formated];
            }
        });
        mSexGraph.getXAxis().setAxisMinimum(0);
        mSexGraph.getXAxis().setAxisMaximum(3);
        mSexGraph.getXAxis().setGranularity(1f);
        mSexGraph.setData(barData);
        mSexGraph.getXAxis().setCenterAxisLabels(true);
        mSexGraph.getXAxis().setLabelCount(4, true);
        mSexGraph.getAxisRight().setEnabled(false);
        mSexGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mSexGraph.getDescription().setEnabled(false);
        mSexGraph.animateXY(1000, 1000);
        mSexGraph.invalidate();

    }


    private  void ShowTotalVietNam(DataTotalVietNam dataTotalVietNam)
    {

        String confirm = dataTotalVietNam.getData().getTotalVietNam().getConfirmed().replace(".","");
        int mPositif = Integer.parseInt(confirm);
        int mMeninggal = Integer.parseInt(dataTotalVietNam.getData().getTotalVietNam().getDeaths());
        int mSembuh = Integer.parseInt(dataTotalVietNam.getData().getTotalVietNam().getRecovered());

        mStatPositiveCases.setText(numberSeparator(mPositif));
        mStatDeathCases.setText(numberSeparator(mMeninggal));
        mStatCuredCases.setText(numberSeparator(mSembuh));

    }

    private void showSexGraph(SpecData specData) {

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        float posLaki = (float) specData.getmKasus().getmJenisKelamin().getmDetailedSpecLists().get(0).getValue();
        float posPerempuan = (float) specData.getmKasus().getmJenisKelamin().getmDetailedSpecLists().get(1).getValue();

        float menLaki = (float) specData.getmMeninggal().getmJenisKelamin().getmDetailedSpecLists().get(0).getValue();
        float menPerempuan = (float) specData.getmMeninggal().getmJenisKelamin().getmDetailedSpecLists().get(1).getValue();

        float semLaki = (float) specData.getmSembuh().getmJenisKelamin().getmDetailedSpecLists().get(0).getValue();
        float semPerempuan = (float) specData.getmSembuh().getmJenisKelamin().getmDetailedSpecLists().get(1).getValue();

        float perLaki = (float) specData.getmSembuh().getmJenisKelamin().getmDetailedSpecLists().get(0).getValue();
        float perPerempuan = (float) specData.getmSembuh().getmJenisKelamin().getmDetailedSpecLists().get(1).getValue();

        barEntries.add(new BarEntry(0.5f, new float[] {posLaki, posPerempuan}));
        barEntries.add(new BarEntry(1.5f, new float[] {menLaki, menPerempuan}));
        barEntries.add(new BarEntry(2.5f, new float[] {semLaki, semPerempuan}));
        barEntries.add(new BarEntry(3.5f, new float[] {perLaki, perPerempuan}));

        BarDataSet barDataSet = new BarDataSet(barEntries,"");

        barDataSet.setValueTextSize(12);

        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value) + "%";
            }
        });

        barDataSet.setColors(COLOR_SCHEME[3], COLOR_SCHEME[0]);

        String positive, death, cured, treated, men, woman;

        if (loadLocale.getLocale().equals("en")) {
            positive = "Positive";
            death = "Death";
            cured = "Cured";
            treated = "Treated";
            men = "Men";
            woman = "Woman";
        } else {
            positive = "Positif";
            death = "Meninggal";
            cured = "Sembuh";
            treated = "Dirawat";
            men = "Laki-laki";
            woman = "Perempuan";
        }

        String[] dataSet = {positive, death, cured, treated};

        barDataSet.setStackLabels(new String[]{men, woman});

        BarData barData = new BarData(barDataSet);

        mSexGraph.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int formated = (int) Math.round(value);
                if(formated < 0 || formated > 3) {
                    formated = 0;
                }
                return dataSet[formated];
            }
        });
        mSexGraph.getXAxis().setAxisMinimum(0);
        mSexGraph.getXAxis().setAxisMaximum(4);
        mSexGraph.getXAxis().setGranularity(1f);
        mSexGraph.setData(barData);
        mSexGraph.getXAxis().setCenterAxisLabels(true);
        mSexGraph.getXAxis().setLabelCount(5, true);
        mSexGraph.getAxisRight().setEnabled(false);
        mSexGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mSexGraph.getDescription().setEnabled(false);
        mSexGraph.animateXY(1000, 1000);
        mSexGraph.invalidate();

    }

    private void showAgeGraphVN(SpecDataVN specData) {

        List<VnPatientCase> vnList = specData.getData().getVnPatientCases();

        int total_khoi = 0, total_dang = 0, total_chet = 0;
        int total_khoi1 = 0, total_dang1 = 0, total_chet1 = 0;
        int total_khoi2 = 0, total_dang2 = 0, total_chet2 = 0;
        int total_khoi3 = 0, total_dang3 = 0, total_chet3 = 0;
        int total_khoi4 = 0, total_dang4 = 0, total_chet4 = 0;
        for (VnPatientCase row : vnList) {
            //0-18,19-39,40-59,>=60
            int age = Integer.parseInt(row.getAge());
            String status = row.getStatus();
            if(new String("Đang điều trị").equals(status)){
                total_dang++;
                if(age<=18){
                    total_dang1++;
                }else if(age>19 && age<=39){
                    total_dang2++;
                }else if(age>39 && age<=59){
                    total_dang3++;
                }else{
                    total_dang4++;
                }
            }
            if(new String("Khỏi").equals(status)){
                total_khoi++;
                if(age<=18){
                    total_khoi1++;
                }else if(age>19 && age<=39){
                    total_khoi2++;
                }else if(age>39 && age<=59){
                    total_khoi3++;
                }else{
                    total_khoi4++;
                }
            }
            if(new String("Tử vong").equals(status)){
                total_chet++;
                if(age<=18){
                    total_chet1++;
                }else if(age>19 && age<=39){
                    total_chet2++;
                }else if(age>39 && age<=59){
                    total_chet3++;
                }else{
                    total_chet4++;
                }
            }
        }

        ArrayList<BarEntry> key = new ArrayList<>();
        ArrayList<BarEntry> key2 = new ArrayList<>();
        ArrayList<BarEntry> key3 = new ArrayList<>();

        key.add(new BarEntry(0.5f, (float) total_dang1/total_dang*100));
        key2.add(new BarEntry(0.5f, (float) total_khoi1/total_khoi*100));
        key3.add(new BarEntry(0.5f, (float) total_chet1/total_chet*100));

        key.add(new BarEntry(1.5f, (float) total_dang2/total_dang*100));
        key2.add(new BarEntry(1.5f, (float) total_khoi2/total_khoi*100));
        key3.add(new BarEntry(1.5f, (float) total_chet2/total_chet*100));

        key.add(new BarEntry(2.5f, (float) total_dang3/total_dang*100));
        key2.add(new BarEntry(2.5f, (float) total_khoi3/total_khoi*100));
        key3.add(new BarEntry(2.5f, (float) total_chet3/total_chet*100));

        key.add(new BarEntry(3.5f, (float) total_dang4/total_dang*100));
        key2.add(new BarEntry(3.5f, (float) total_khoi4/total_khoi*100));
        key3.add(new BarEntry(3.5f, (float) total_chet4/total_chet*100));

        List<IBarDataSet> kelompokUmurDS = new ArrayList<>();

        String positive, death, cured, treated;
        String txt_confirmed, txt_deaths, txt_recovered;

        if (loadLocale.getLocale().equals("en")) {
            txt_confirmed = "Đang điều trị";
            txt_recovered = "Khỏi";
            txt_deaths = "Tử vong";
        } else {
            txt_confirmed = "Đang điều trị";
            txt_recovered = "Khỏi";
            txt_deaths = "Tử vong";
        }

        BarDataSet barDataSet = new BarDataSet(key, txt_confirmed);
        barDataSet.setColor(COLOR_SCHEME[0]);
        barDataSet.setValueTextSize(8);
        kelompokUmurDS.add(barDataSet);

        BarDataSet barDataSet2 = new BarDataSet(key2, txt_recovered);
        barDataSet2.setColor(COLOR_SCHEME[1]);
        barDataSet2.setValueTextSize(8);
        kelompokUmurDS.add(barDataSet2);

        BarDataSet barDataSet3 = new BarDataSet(key3, txt_deaths);
        barDataSet3.setColor(COLOR_SCHEME[2]);
        barDataSet3.setValueTextSize(8);
        kelompokUmurDS.add(barDataSet3);

        BarData kelompokUmurBD = new BarData(barDataSet, barDataSet2, barDataSet3);
        mAgeGraph.setData(kelompokUmurBD);
        //0-18,19-39,40-59,>=60
        String name1 = "0-18";
        String name2 = "19-39";
        String name3 = "40-59";
        String name4 = ">=60";
        String[] dataSet = {name1, name2, name3, name4};
        mAgeGraph.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int formated = (int) Math.round(value);
                if(formated < 0 || formated>=15) {
                    formated = 0;
                }
                if(formated==0){formated=0;}
                if(formated==4){formated=1;}
                if(formated==7){formated=2;}
                if(formated==11){formated=3;}
                return dataSet[formated];
            }
        });

        mAgeGraph.getXAxis().setCenterAxisLabels(true);

        final float BAR_WIDTH = 1f;
        final float GROUP_SPACE = 0.5f;
        final float BAR_SPACE = 0.05f;
        final float GROUP_COUNT = 4;
        mAgeGraph.getBarData().setBarWidth(BAR_WIDTH);
        // restrict the x-axis range
        mAgeGraph.getXAxis().setAxisMinimum(0);
//        mAgeGraph.getLegend().setTextSize(12);
        mAgeGraph.getXAxis().setAxisMaximum(0 + mAgeGraph.getBarData().getGroupWidth(GROUP_SPACE, BAR_SPACE) * GROUP_COUNT);
        //mAgeGraph.getXAxis().setAxisMaximum(4);
        mAgeGraph.getXAxis().setLabelCount(5, true);
        mAgeGraph.groupBars(0, GROUP_SPACE, BAR_SPACE);
        mAgeGraph.getAxisRight().setEnabled(false);
        mAgeGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        Description description = new Description();
        description.setText("");
        mAgeGraph.setDescription(description);
        mAgeGraph.animateXY(1000, 1000);
        mAgeGraph.invalidate();

    }

    private void showAgeGraph(SpecData specData) {

        List<SpecData.DetailedData.DerivativeDetailedData.DetailedSpecList> kelompokUmurPos =
                specData.getmKasus().getmKelompokUmur().getmDetailedSpecLists();
        List<SpecData.DetailedData.DerivativeDetailedData.DetailedSpecList> kelompokUmurMen =
                specData.getmMeninggal().getmKelompokUmur().getmDetailedSpecLists();
        List<SpecData.DetailedData.DerivativeDetailedData.DetailedSpecList> kelompokUmurSem =
                specData.getmSembuh().getmKelompokUmur().getmDetailedSpecLists();
        List<SpecData.DetailedData.DerivativeDetailedData.DetailedSpecList> kelompokUmurPer =
                specData.getmPerawatan().getmKelompokUmur().getmDetailedSpecLists();

        List<IBarDataSet> kelompokUmurDS = new ArrayList<>();

        ArrayList<BarEntry> key = new ArrayList<>();
        ArrayList<BarEntry> key2 = new ArrayList<>();
        ArrayList<BarEntry> key3 = new ArrayList<>();
        ArrayList<BarEntry> key4 = new ArrayList<>();

        for (int i = 0; i < kelompokUmurPos.size(); i++) {
            key.add(new BarEntry(i, (float) kelompokUmurPos.get(i).getValue()));
            key2.add(new BarEntry(i, (float) kelompokUmurMen.get(i).getValue()));
            key3.add(new BarEntry(i, (float) kelompokUmurSem.get(i).getValue()));
            key4.add(new BarEntry(i, (float) kelompokUmurPer.get(i).getValue()));
        }

        String positive, death, cured, treated;

        if (loadLocale.getLocale().equals("en")) {
            positive = "Positive";
            death = "Death";
            cured = "Cured";
            treated = "Treated";
        } else {
            positive = "Positif";
            death = "Meninggal";
            cured = "Sembuh";
            treated = "Dirawat";
        }

        BarDataSet barDataSet = new BarDataSet(key, positive);
        barDataSet.setColor(COLOR_SCHEME[0]);
        barDataSet.setValueTextSize(8);
        kelompokUmurDS.add(barDataSet);

        BarDataSet barDataSet2 = new BarDataSet(key2, death);
        barDataSet2.setColor(COLOR_SCHEME[1]);
        barDataSet2.setValueTextSize(8);
        kelompokUmurDS.add(barDataSet2);

        BarDataSet barDataSet3 = new BarDataSet(key3, cured);
        barDataSet3.setColor(COLOR_SCHEME[2]);
        barDataSet3.setValueTextSize(8);
        kelompokUmurDS.add(barDataSet3);

        BarDataSet barDataSet4 = new BarDataSet(key4, treated);
        barDataSet4.setValueTextSize(8);
        barDataSet4.setColor(COLOR_SCHEME[3]);
        kelompokUmurDS.add(barDataSet4);

        BarData kelompokUmurBD = new BarData(barDataSet, barDataSet2, barDataSet3, barDataSet4);
        mAgeGraph.setData(kelompokUmurBD);
        mAgeGraph.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int formated = Math.round(value);
                // rejecting the unused data that case error on getting the list
                if (formated > 5 || formated < 0) {
                    formated = 0;
                }
                return specData.getmKasus().getmKelompokUmur().getmDetailedSpecLists().get(formated).getKey();
            }
        });

        mAgeGraph.getXAxis().setCenterAxisLabels(true);

        final float BAR_WIDTH = 0.15f;
        final float GROUP_SPACE = 0.10f;
        final float BAR_SPACE = 0.05f;
        final float GROUP_COUNT = 6;

        mAgeGraph.getBarData().setBarWidth(BAR_WIDTH);

        // restrict the x-axis range
        mAgeGraph.getXAxis().setAxisMinimum(0);
//        mAgeGraph.getLegend().setTextSize(12);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mAgeGraph.getXAxis().setAxisMaximum(0 + mAgeGraph.getBarData().getGroupWidth(GROUP_SPACE, BAR_SPACE) * GROUP_COUNT);
        mAgeGraph.groupBars(0, GROUP_SPACE, BAR_SPACE);
        mAgeGraph.getAxisRight().setEnabled(false);
        mAgeGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        Description description = new Description();
        description.setText("");
        mAgeGraph.setDescription(description);
        mAgeGraph.animateXY(1000, 1000);
        mAgeGraph.invalidate();

    }

    private void showConditionGraph(SpecData specData) {

        List<SpecData.DetailedData.DerivativeDetailedData.DetailedSpecList> detailedSpecLists =
                specData.getmKasus().getmKondisiPenyerta().getmDetailedSpecLists();

        List<IBarDataSet> iBarDataSets = new ArrayList<>();

        for (int i = 0; i < detailedSpecLists.size(); i++) {
            ArrayList<BarEntry> key = new ArrayList<>();
            key.add(new BarEntry(i, (float) detailedSpecLists.get(i).getValue()));
            // Manipulating first letter to be capitalized
            String theDataSet = detailedSpecLists.get(i).getKey();
            if (loadLocale.getLocale().equals("en")) {
                theDataSet = translatedCondition(theDataSet);
            }
            BarDataSet barDataSet = new BarDataSet(key, theDataSet.substring(0, 1).toUpperCase() + theDataSet.substring(1).toLowerCase());
            barDataSet.setColor(COLOR_SCHEME[i]);
            iBarDataSets.add(barDataSet);
        }
        BarData barData = new BarData(iBarDataSets);
        barData.setValueFormatter(new ValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value) + "%";
            }
        });

        barData.setValueTextSize(10);
        mConditionGraph.setData(barData);
        mConditionGraph.setMinimumHeight(180);
        mConditionGraph.getXAxis().setDrawLabels(false);
        mConditionGraph.getXAxis().setDrawAxisLine(false);
        mConditionGraph.getXAxis().setDrawGridLines(false);
        mConditionGraph.getAxisRight().setEnabled(false);
        mConditionGraph.getLegend().setWordWrapEnabled(true);
        mConditionGraph.getLegend().setTextSize(10);
        Description description = new Description();
        description.setText("");
        mConditionGraph.setDescription(description);
        mConditionGraph.setDoubleTapToZoomEnabled(false);
        mConditionGraph.setPinchZoom(false);
        mConditionGraph.animateXY(1000, 1000);
        mConditionGraph.invalidate();
    }

    private void hideSpecDataLoading() {

        mConditionGraphShimmer.setVisibility(View.GONE);
        mAgeGraphShimmer.setVisibility(View.GONE);
        mSexGraphShimmer.setVisibility(View.GONE);
        mSymptomGraphShimmer.setVisibility(View.GONE);
        mConditionGraph.setVisibility(View.VISIBLE);
        //mAgeGraph.setVisibility(View.VISIBLE);
        mSexGraph.setVisibility(View.VISIBLE);
        mSymptomGraph.setVisibility(View.VISIBLE);

    }

    private void showSpecDataLoading() {

        mConditionGraphShimmer.setVisibility(View.VISIBLE);
        mAgeGraphShimmer.setVisibility(View.VISIBLE);
        mSexGraphShimmer.setVisibility(View.VISIBLE);
        mSymptomGraphShimmer.setVisibility(View.VISIBLE);
        mConditionGraph.setVisibility(View.GONE);
        //mAgeGraph.setVisibility(View.GONE);
        mSexGraph.setVisibility(View.GONE);
        mSymptomGraph.setVisibility(View.GONE);

    }


//    private void showNewCaseGraph(RegulerDataVN regulerData) {
//
//        ArrayList lineDataConfirmed = new ArrayList();
//        ArrayList lineDataDeaths = new ArrayList();
//        ArrayList lineDataRecovered = new ArrayList();
//
//        //List<RegulerData.UpdatedData.DailyData> dailyDataList = regulerData.getUpdatedData().getDailyData();
//        List<TrendlineVnCase> dailyDataList = regulerData.getData().getTrendlineVnCases();
//
//        for (TrendlineVnCase theDailyData : dailyDataList) {
//            String confirmed = theDailyData.getConfirmed();//nhiem
//            if(confirmed == null) {
//                confirmed = "0";
//            }
//            String deaths = theDailyData.getDeaths();//die
//            if(deaths == null) {
//                deaths = "0";
//            }
//            String recovered = theDailyData.getRecovered();//khoi
//            if(recovered == null) {
//                recovered = "0";
//            }
//            int data_confirmed = Integer.parseInt(confirmed);
//            int data_deaths = Integer.parseInt(deaths);
//            int data_recovered = Integer.parseInt(recovered);
//
//            try {
//                Date date = new SimpleDateFormat("yyyyMMdd").parse(theDailyData.getDate());
//                long dataTanggal = date.getTime();
//
//                lineDataConfirmed.add(new Entry(dataTanggal, data_confirmed));
//                lineDataDeaths.add(new Entry(dataTanggal, data_deaths));
//                lineDataRecovered.add(new Entry(dataTanggal, data_recovered));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        String txt_confirmed, txt_deaths, txt_recovered;
//
//        if (loadLocale.getLocale().equals("en")) {
//            txt_confirmed = "Ca nhiễm";
//            txt_deaths = "Ca tử vong";
//            txt_recovered = "Ca khỏi";
//        } else {
//            txt_confirmed = "Ca nhiễm";
//            txt_deaths = "Ca tử vong";
//            txt_recovered = "Ca khỏi";
//        }
//
//        LineDataSet lineDataSetConfirmed = new LineDataSet(lineDataConfirmed, txt_confirmed);
//        LineDataSet lineDataSetDeaths = new LineDataSet(lineDataDeaths, txt_deaths);
//        LineDataSet lineDataSetRecovered = new LineDataSet(lineDataRecovered, txt_recovered);
//
//        setupLineChart(lineDataSetConfirmed, "#ffb259");
//        setupLineChart(lineDataSetDeaths, "ff5959");//ff5959
//        setupLineChart(lineDataSetRecovered, "4cd97b");//4cd97b
//
//        mNewCaseGraph.animateY(1000);
//        mNewCaseGraph.getAxisRight().setEnabled(false);
//        mNewCaseGraph.setClickable(false);
//        mNewCaseGraph.setDoubleTapToZoomEnabled(false);
//        mNewCaseGraph.setScaleEnabled(false);
//        mNewCaseGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        mNewCaseGraph.getXAxis().setValueFormatter(new ValueFormatter() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public String getFormattedValue(float value) {
//                long millisecond = (long) value;
//                String dateString = DateFormat.format("dd MMM", new Date(millisecond)).toString();
//                return dateString;
//            }
//        });
//
//        Description desc = new Description();
//        desc.setText("");
//        mNewCaseGraph.setDescription(desc);
//
//        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(lineDataSetConfirmed);
//        dataSets.add(lineDataSetDeaths);
//        dataSets.add(lineDataSetRecovered);
//
//        LineData data = new LineData(dataSets);
//        mNewCaseGraph.setData(data);
//        mNewCaseGraph.invalidate();
//
//    }


    private void showCumulativeCaseGraph(RegulerDataVN regulerData) {

        ArrayList lineDataConfirmed = new ArrayList();
        ArrayList lineDataDeaths = new ArrayList();
        ArrayList lineDataRecovered = new ArrayList();

        //List<RegulerData.UpdatedData.DailyData> dailyDataList = regulerData.getUpdatedData().getDailyData();
        List<TrendlineVnCase> dailyDataList = regulerData.getData().getTrendlineVnCases();

        for (TrendlineVnCase theDailyData : dailyDataList) {
            String confirmed = theDailyData.getConfirmed();//nhiem
            if(confirmed == null) {
                confirmed = "0";
            }
            String deaths = theDailyData.getDeaths();//die
            if(deaths == null) {
                deaths = "0";
            }
            String recovered = theDailyData.getRecovered();//khoi
            if(recovered == null) {
                recovered = "0";
            }
            int data_confirmed = Integer.parseInt(confirmed);
            int data_deaths = Integer.parseInt(deaths);
            int data_recovered = Integer.parseInt(recovered);

            try {
                Date date = new SimpleDateFormat("yyyyMMdd").parse(theDailyData.getDate());
                long dataTanggal = date.getTime();

                lineDataConfirmed.add(new Entry(dataTanggal, data_confirmed));
                lineDataDeaths.add(new Entry(dataTanggal, data_deaths));
                lineDataRecovered.add(new Entry(dataTanggal, data_recovered));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String txt_confirmed, txt_deaths, txt_recovered;

        if (loadLocale.getLocale().equals("en")) {
            txt_confirmed = "Ca nhiễm";
            txt_deaths = "Ca tử vong";
            txt_recovered = "Ca khỏi";
        } else {
            txt_confirmed = "Ca nhiễm";
            txt_deaths = "Ca tử vong";
            txt_recovered = "Ca khỏi";
        }

        LineDataSet lineDataSetConfirmed = new LineDataSet(lineDataConfirmed, txt_confirmed);
        LineDataSet lineDataSetDeaths = new LineDataSet(lineDataDeaths, txt_deaths);
        LineDataSet lineDataSetRecovered = new LineDataSet(lineDataRecovered, txt_recovered);

        setupLineChart(lineDataSetConfirmed, "#ffb259");
        setupLineChart(lineDataSetDeaths, "ff5959");//ff5959
        setupLineChart(lineDataSetRecovered, "4cd97b");//4cd97b

        mCumulativeCaseGraph.animateY(1000);
        mCumulativeCaseGraph.getAxisRight().setEnabled(false);
//        mLineChart.getLegend().setTextSize(12);
        mCumulativeCaseGraph.setClickable(false);
        mCumulativeCaseGraph.setDoubleTapToZoomEnabled(false);
        mCumulativeCaseGraph.setScaleEnabled(false);
        mCumulativeCaseGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mCumulativeCaseGraph.getXAxis().setValueFormatter(new ValueFormatter() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public String getFormattedValue(float value) {
                long millisecond = (long) value;
                String dateString = DateFormat.format("dd MMM", new Date(millisecond)).toString();
                return dateString;
            }
        });

        Description desc = new Description();
        desc.setText("");
        mCumulativeCaseGraph.setDescription(desc);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSetConfirmed);
        dataSets.add(lineDataSetDeaths);
        dataSets.add(lineDataSetRecovered);

        LineData data = new LineData(dataSets);
        mCumulativeCaseGraph.setData(data);
        mCumulativeCaseGraph.invalidate();

    }

    private void setupLineChart(LineDataSet lineDataSet, String s) {
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColor(rgb(s));
        lineDataSet.setFillColor(rgb(s));
        lineDataSet.setDrawValues(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
    }
    /*
    private void showRegulerData(RegulerData regulerData) {

        int mPositif = regulerData.getUpdatedData().getTotalCases().getmPositif();
        int mMeninggal = regulerData.getUpdatedData().getTotalCases().getmMeninggal();
        int mSembuh = regulerData.getUpdatedData().getTotalCases().getmSembuh();
        int mODP = regulerData.getDerivativeData().getmODP();
        int mPDP = regulerData.getDerivativeData().getmPDP();
        int mAddedPos = regulerData.getUpdatedData().getNewCases().getmPositif();
        int mAddedMen = regulerData.getUpdatedData().getNewCases().getmMeninggal();
        int mAddedSem = regulerData.getUpdatedData().getNewCases().getmSembuh();
        String mUpdate = regulerData.getUpdatedData().getNewCases().getmWaktuUpdate();

        mStatPositiveCases.setText(numberSeparator(mPositif));
        mStatDeathCases.setText(numberSeparator(mMeninggal));
        mStatCuredCases.setText(numberSeparator(mSembuh));
        mStatMonitoringCases.setText(numberSeparator(mODP));
        mStatPatientCases.setText(numberSeparator(mPDP));
        mStatAddedPositive.setText("+" + numberSeparator(mAddedPos));
        mStatAddedDeath.setText("+" + numberSeparator(mAddedMen));
        mStatAddedCured.setText("+" + numberSeparator(mAddedSem));

        if (loadLocale.getLocale().equals("en")) {
            mUpdatedDate.setText("Updated on: " + mUpdate);
        } else {
            mUpdatedDate.setText("Diperbarui pada: " + mUpdate);
        }

    }
    */
    private void hideRegularDataLoading() {
        mBoxShimmer.setVisibility(View.GONE);
        mCumulativeGraphShimmer.setVisibility(View.GONE);
        mNewCaseGraphShimmer.setVisibility(View.GONE);
        mBoxLayout.setVisibility(View.VISIBLE);
        mCumulativeCaseGraph.setVisibility(View.VISIBLE);
        //mNewCaseGraph.setVisibility(View.VISIBLE);
    }

    private void showRegularDataLoading() {
        mBoxShimmer.setVisibility(View.VISIBLE);
        mCumulativeGraphShimmer.setVisibility(View.VISIBLE);
        mNewCaseGraphShimmer.setVisibility(View.VISIBLE);
        mBoxLayout.setVisibility(View.GONE);
        mCumulativeCaseGraph.setVisibility(View.GONE);
        //mNewCaseGraph.setVisibility(View.GONE);
    }

    private String numberSeparator(int value) {
        return String.valueOf(NumberFormat.getNumberInstance(Locale.ITALY).format(value));
    }

    private String isUsable(double value) {
        if (loadLocale.getLocale().equals("en")) {
            if (value < 50) {
                return "\nCAN'T BE USED AS A REFERENCE";
            }
            return "";
        } else {
            if (value < 50) {
                return "\nDATA TIDAK LENGKAP";
            }
            return "";
        }

    }

    private String translatedCondition(String condition) {
        switch (condition.toLowerCase()) {
            case "hipertensi": return "Hypertension";
            case "penyakit jantung": return "Heart Disease";
            case "penyakit paru obstruktif kronis": return "Chronic Obstructive Pulmonary Disease";
            case "gangguan napas lain": return "Breathing Disorders";
            case "penyakit ginjal": return "Kidney Illnes";
            case "hamil": return "Pregnant";
            case "asma": return "Asthma";
            case "kanker": return "Cancer";
            case "tbc": return "TBC";
            case "penyakit hati": return "Liver Disease";
            case "gangguan imun": return "Immune Disorders";
            case "obesitas": return "Obesity";
            default: return "Unidentified";
        }
    }

    private String translatedSymptom(String symptom) {
        switch (symptom.toLowerCase()) {
            case "batuk": return "Cough";
            case "demam": return "Fever";
            case "sesak napas": return "Hard to breath";
            case "lemas": return "Limp";
            case "riwayat demam": return "Had a fever before";
            case "sakit tenggorokan": return "Sore throat";
            case "pilek": return "Cold";
            case "sakit kepala": return "Headache";
            case "mual": return "Nausea";
            case "keram otot": return "Muscle cramp";
            case "menggigil": return "Shivering";
            case "diare": return "Diarrhea";
            case "sakit perut": return "Stomach ache";
            case "lain-lain": return "etc.";
            default: return "Unidentified";
        }
    }

    private static final int[] COLOR_SCHEME = {
            rgb("#ffb259"), rgb("#ff5959"), rgb("4cd97b"), rgb("4cb5ff"), rgb("9059ff"),
            rgb("#ff3434"), rgb("#ffeeee"), rgb("4c9a9a"), rgb("4c5b5b"), rgb("90ff75"),
            rgb("#900407"), rgb("#ddddee"), rgb("fcfbfb"), rgb("000f05"),
    };

}
