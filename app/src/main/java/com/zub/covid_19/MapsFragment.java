package com.zub.covid_19;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zub.covid_19.adapter.ProvAdapter;
import com.zub.covid_19.adapter.ProvVNAdapter;
import com.zub.covid_19.api.ProvincesVietNam.DataProvinceVN;
import com.zub.covid_19.api.ProvincesVietNam.Province;
import com.zub.covid_19.api.ProvincesVietNam.ProvinceVN;
import com.zub.covid_19.api.TotalVietNam.DataTotalVietNam;
import com.zub.covid_19.api.provData.ProvData;
import com.zub.covid_19.api.specData.Data;
import com.zub.covid_19.api.specData.SpecDataVN;
import com.zub.covid_19.api.specData.SumMapVNProvinces;
import com.zub.covid_19.api.specData.VnPatientCase;
import com.zub.covid_19.ui.BottomSheetMapsDialog;
import com.zub.covid_19.util.LoadLocale;
import com.zub.covid_19.vm.ProvDataVNViewModel;
import com.zub.covid_19.vm.ProvDataViewModel;
import com.zub.covid_19.vm.SpecDataViewModelVN;
import com.zub.covid_19.vm.TotalVietNamViewModel;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MapsFragment extends Fragment implements
        OnMapReadyCallback,
        ProvVNAdapter.ListClickedListener {

    private ProvAdapter provAdapter;

    private ProvVNAdapter provVNAdapter;

    private DataProvinceVN provListData = new DataProvinceVN();

    private DataProvinceVN filteredList = new DataProvinceVN();

    private Data filteredListVN = new Data();

    private GoogleMap googleMap;

    private SlidingUpPanelLayout mSlideUpLayout;

    private SearchView mFilter;

    private TextView mProvCollectedData;

    private ArrayList<Marker> markerArrayList = new ArrayList<>();

    private LinearLayout mProvDetailedCaseButton;

    private Marker marker;
    private List<VnPatientCase> list_vnPatientCase;
    private Map<String, SumMapVNProvinces> listALL = new HashMap<>();
    private int arraySizeBefore;
    float totalConfirm = 1;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);


        SpecDataViewModelVN specDataViewModelVN;
        specDataViewModelVN = ViewModelProviders.of(this).get(SpecDataViewModelVN.class);
        specDataViewModelVN.init();
        specDataViewModelVN.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    //showSpecDataLoading();
                } else {
                    //hideSpecDataLoading();
                }
            }
        });
        specDataViewModelVN.getSpecData().observe(this, new Observer<SpecDataVN>() {
            @Override
            public void onChanged(SpecDataVN specDataVN) {
                //showAgeGraphVN(specDataVN);
                //showSexGraphVN(specDataVN);
                //showUpdatedDateVN(specDataVN);

                for(VnPatientCase row :specDataVN.getData().getVnPatientCases()){
                    String location = row.getLocation();
                    String gender = row.getGender();
                    int age = Integer.parseInt(row.getAge());
                    SumMapVNProvinces item = new SumMapVNProvinces();
                    if(listALL.get(location)==null)
                    {
                        if(gender.equals("Nam"))
                        {
                            item.setNam(1);
                        }
                        else
                        {
                            item.setNu(1);
                        }

                        if(age<=18){
                            item.setType_1(1);
                        }else if(age>19 && age<=39){
                            item.setType_2(1);
                        }else if(age>39 && age<=59){
                            item.setType_3(1);
                        }else{
                            item.setType_4(1);
                        }

                        listALL.put(location,item);


                    }
                    else
                    {
                        SumMapVNProvinces update = listALL.get(location);
                        if(gender.equals("Nam"))
                        {
                            update.setNam(update.getNam()+1);
                        }
                        else
                        {
                            update.setNu(update.getNu()+1);
                        }

                        if(age<=18){
                            update.setType_1(update.getType_1()+1);
                        }else if(age>19 && age<=39){
                            update.setType_2(update.getType_2()+1);
                        }else if(age>39 && age<=59){
                            update.setType_3(update.getType_3()+1);
                        }else{
                            update.setType_4(update.getType_4()+1);
                        }



                    }



                }

                Log.d("test",listALL.get("Hồ Chí Minh").getNam()+"");

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
                String confirm  =dataTotalVietNam.getData().getTotalVietNam().getConfirmed().replace(".","");
                totalConfirm = Float.parseFloat(confirm);
            }



        });


        mProvCollectedData = view.findViewById(R.id.prov_collected_data);

        mProvDetailedCaseButton = view.findViewById(R.id.prov_detailed_case);

        mSlideUpLayout = view.findViewById(R.id.sliding_layout);

        mFilter = view.findViewById(R.id.prov_filter);

        mFilter.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mSlideUpLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        mFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!provListData.getProvinces().isEmpty()) {
                    filter(s);
                }
                return false;
            }
        });










        return view;
    }

    private void filter(String toString) {

        arraySizeBefore = markerArrayList.size();

        filteredList.getProvinces().clear();
        googleMap.clear();
        marker.remove();
        for (Province theProvData : provListData.getProvinces()) {
            if (theProvData.getProvinceName().toLowerCase().contains(toString.toLowerCase())) {
                filteredList.getProvinces().add(theProvData);
                double lat = Double.parseDouble(theProvData.getLat());
                double lng = Double.parseDouble(theProvData.getLong());
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng));
                marker = googleMap.addMarker(markerOptions);
                markerArrayList.add(marker);
            }
        }

        provVNAdapter.filterList(filteredList);
    }

    private void setupRecyclerView(RecyclerView mProvRecyclerView, ProvinceVN provData) {


        List<Province> provListData = provData.getData().getProvinces();

        this.provListData.getProvinces().addAll(provListData);
        this.filteredList.getProvinces().addAll(provListData);

        provVNAdapter = new ProvVNAdapter(this.provListData, this);
        mProvRecyclerView.setAdapter(provVNAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mProvRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void hideLoading(ShimmerFrameLayout mProvCardShimmer, RecyclerView mProvRecyclerView) {

        mProvCardShimmer.setVisibility(View.GONE);

        mProvRecyclerView.setVisibility(View.VISIBLE);

    }

    private void showLoading(ShimmerFrameLayout mProvCardShimmer, RecyclerView mProvRecyclerView) {

        mProvCardShimmer.setVisibility(View.VISIBLE);

        mProvRecyclerView.setVisibility(View.GONE);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mapView = view.findViewById(R.id.prov_map_view);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(this.getContext()));

        this.googleMap = googleMap;

        final double LAT = 	10.762622d;
        final double LNG = 106.660172d;
        final float ZOOM = 5f;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(LAT, LNG), ZOOM);

        googleMap.moveCamera(cameraUpdate);

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.getContext(), R.raw.style_json));

        //PROV DATA WIDGET

        ShimmerFrameLayout mProvCardShimmer = Objects.requireNonNull(this.getView()).findViewById(R.id.prov_shimmer);

        RecyclerView mProvRecyclerView = this.getView().findViewById(R.id.prov_rv);

        //PROV DATA FETCHING



        ProvDataVNViewModel provDataViewModel;

        provDataViewModel = ViewModelProviders.of(this).get(ProvDataVNViewModel.class);
        provDataViewModel.init();

        provDataViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showLoading(mProvCardShimmer, mProvRecyclerView);
                } else {
                    hideLoading(mProvCardShimmer, mProvRecyclerView);
                }
            }
        });

        provDataViewModel.getRegulerData().observe(this, new Observer<ProvinceVN>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onChanged(ProvinceVN provinceVN) {



                List<Province> provListData = provinceVN.getData().getProvinces();

                for (Province theProvListData : provListData) {

                    double lat = -1;
                    double lng = -1;
                    if (!(theProvListData.getLat() == null)) {
                        String Lat = theProvListData.getLat();
                        String Lng = theProvListData.getLong();
                        lat = Double.parseDouble(Lat);
                        lng = Double.parseDouble(Lng);
                    }
                    LatLng latLng = new LatLng(lat, lng);
                    marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                    markerArrayList.add(marker);
                }

                LoadLocale loadLocale = new LoadLocale(getActivity());

//                if (loadLocale.getLocale().equals("en")) {
//                    mProvCollectedData.setText("Data collected: " +
//                            String.format("%.1f",provData.getCurrentData()) +
//                            "% on " + provData.getLastUpdate());
//                } else {
//                    mProvCollectedData.setText("Data dihimpun: " +
//                            String.format("%.1f",provData.getCurrentData()) +
//                            "% pada " + provData.getLastUpdate());
//                }

//                mProvCollectedData.setText("Data collected: " +
//                        String.format("%.1f",provData.getCurrentData()) +
//                        "% on " + provData.getLastUpdate());

                googleMap.setInfoWindowAdapter(new ProvInfoWindowAdapter());

                setupBottomSheet(mProvDetailedCaseButton, provinceVN);

                setupRecyclerView(mProvRecyclerView, provinceVN);
            }






        });

    }

    private void setupBottomSheet(LinearLayout mProvDetailedCaseButton, ProvinceVN provData) {
        Toast mToast = Toast.makeText(getContext(), "", Toast.LENGTH_LONG);
//        BottomSheetMapsDialog bottomSheetMapsDialog = new BottomSheetMapsDialog();

        mProvDetailedCaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), bottomSheetMapsDialog.getClass());
//                intent.putExtra("provData", provData);
//                startActivity(intent);
//                bottomSheetMapsDialog.show(getFragmentManager(),"BottomSheet");
                mToast.setText("Other data coming soon!");
                mToast.show();
            }
        });

    }

    @Override
    public void onListClicked(int position) {


        int zoom = (int) googleMap.getCameraPosition().zoom;

        mFilter.clearFocus();
        markerArrayList.get(position + arraySizeBefore).showInfoWindow();
        double LAT =  Double.parseDouble(filteredList.getProvinces().get(position).getLat()) + 0.655d;
        double LNG = Double.parseDouble( filteredList.getProvinces().get(position).getLong());
        final float ZOOM = 7;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(LAT, LNG), ZOOM);

        final Handler handler = new Handler();

        //Tricky part that collapse the slide up panel after 200ms (the keyboard already hide)
        //Can't find another approach to do the same

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                googleMap.animateCamera(cameraUpdate, 1000, null);
                mSlideUpLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        },200);

    }

    private String numberSeparator(int value) {
        return String.valueOf(NumberFormat.getNumberInstance(Locale.ITALY).format(value));
    }

    private class ProvInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        View view = getLayoutInflater().inflate(R.layout.info_window_prov, null);

        @SuppressLint({"CutPasteId", "DefaultLocale"})
        @Override
        public View getInfoWindow(Marker marker) {
            TextView mProvName, mProvCase, mProvDeath, mProvCured, mProvTreated, mProvPercentage,
                    mProvMale, mProvFemale, mProvBaby, mProvTeen, mProvMan, mProvAdult, mProvOld, mProvGrandParents;

            mProvName = view.findViewById(R.id.info_window_prov_name);
            mProvCase = view.findViewById(R.id.info_window_prov_case);
            mProvDeath = view.findViewById(R.id.info_window_prov_death);
            mProvCured = view.findViewById(R.id.info_window_prov_cured);
            //mProvTreated = view.findViewById(R.id.info_window_prov_treated);
            mProvPercentage = view.findViewById(R.id.info_window_prov_percentage);
            mProvMale = view.findViewById(R.id.info_window_prov_male);
            mProvFemale = view.findViewById(R.id.info_window_prov_female);
            mProvBaby = view.findViewById(R.id.info_window_prov_age_baby);
            mProvTeen = view.findViewById(R.id.info_window_prov_age_teen);
           // mProvMan = view.findViewById(R.id.info_window_prov_age_man);
            mProvAdult = view.findViewById(R.id.info_window_prov_age_adult);
            mProvOld = view.findViewById(R.id.info_window_prov_age_old);
            //mProvGrandParents = view.findViewById(R.id.info_window_prov_age_grandparent);

            //remove the "m" from getId() to returning integer

            String id = marker.getId();
            int convId = Integer.parseInt(id.replaceAll("[^\\d.]", "")) - arraySizeBefore;

            Province provListData = filteredList.getProvinces().get(convId);

            //HANDLING ERROR CAUSE NULL __ NOT THE EFFECTIVE WAY
            //BECAUSE WE FORCE TO PUT THE FIRST ageList DATA

//            ArrayList<Integer> ageList = new ArrayList<>();
//
//            for (ProvData.ProvListData.ProvDataAgeList theAgeList : provListData.getProvDataAgeLists()) {
//                if (provListData.getProvDataAgeLists().size() < 6) {
//                    if (ageList.size() == 0) {
//                        ageList.add(0);
//                    }
//                }
//                ageList.add(theAgeList.getDocCount());
//            }

            mProvName.setText(provListData.getProvinceName());
            mProvCase.setText(numberSeparator( Integer.parseInt(provListData.getConfirmed())));
            mProvDeath.setText(numberSeparator(Integer.parseInt(provListData.getDeaths())));
            mProvCured.setText(numberSeparator(Integer.parseInt(provListData.getRecovered())));

          //  mProvTreated.setText(numberSeparator(provListData.getTreatedAmount()));





            float percent = Float.parseFloat(provListData.getConfirmed()) * 100.0f / totalConfirm;
            float scale = (float) Math.pow(10, 2);
            float per = Math.round(percent * scale) / scale;
            mProvPercentage.setText(String.format("%.2f", per));
            mProvMale.setText(numberSeparator(listALL.get(provListData.getProvinceName()).getNam()));
            mProvFemale.setText(numberSeparator(listALL.get(provListData.getProvinceName()).getNu()));

            mProvBaby.setText(numberSeparator(listALL.get(provListData.getProvinceName()).getType_1()));
            mProvTeen.setText(numberSeparator(listALL.get(provListData.getProvinceName()).getType_2()));
            mProvOld.setText(numberSeparator(listALL.get(provListData.getProvinceName()).getType_4()));
            mProvAdult.setText(numberSeparator(listALL.get(provListData.getProvinceName()).getType_3()));
//           setText(numberSeparator(listALL.get(provListData.getProvinceName()).getType_3()));
            //mProvGrandParents.setText(numberSeparator(ageList.get(5)));

            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    }



}