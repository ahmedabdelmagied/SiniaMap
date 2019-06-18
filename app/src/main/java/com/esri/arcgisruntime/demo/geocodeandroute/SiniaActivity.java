package com.esri.arcgisruntime.demo.geocodeandroute;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.view.MapView;


import java.io.File;

public class SiniaActivity extends AppCompatActivity{


  private final String extern = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();


 // private final String extern = Environment.getDataDirectory().getAbsolutePath();
  private MapView mMapView;
  private MobileMapPackage mapPackage;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_geocode_route);
    Toolbar toolbar = (Toolbar) findViewById(com.esri.arcgisruntime.demo.geocodeandroute.R.id.toolbar);
    setSupportActionBar(toolbar);

    // inflate MapView from layout
    mMapView = (MapView) findViewById(com.esri.arcgisruntime.demo.geocodeandroute.R.id.mapView);

//        MapOptions mapOptions = new MapOptions(MapOptions.MapType.TOPO,33.576295453819419,29.612834859103543,6);
//        mMapView.setMapOptions(mapOptions);
    loadMobileMapPackage(getMmpkPath());

  }


  /**
   * Load a mobile map package into a MapView
   *
   * @param mmpkFile Full path to mmpk file
   */
  private void loadMobileMapPackage(String mmpkFile) {
    //[DocRef: Name=Open Mobile Map Package-android, Category=Work with maps, Topic=Create an offline map]
    // create the mobile map package
    mapPackage = new MobileMapPackage(mmpkFile);
    // load the mobile map package asynchronously
    mapPackage.loadAsync();

    // add done listener which will invoke when mobile map package has loaded
    mapPackage.addDoneLoadingListener(new Runnable() {
      @Override
      public void run() {
        // check load status and that the mobile map package has maps
        if (mapPackage.getLoadStatus() == LoadStatus.LOADED && !mapPackage.getMaps().isEmpty()) {
          // add the map from the mobile map package to the MapView
          mMapView.setMap(mapPackage.getMaps().get(0));
        }
      }
    });
    //[DocRef: END]
  }




  private String getMmpkPath() {
    // NOTE: Update this path value to suit your own datasets.
    File mmpkFile = new File(extern , getString(com.esri.arcgisruntime.demo.geocodeandroute.R.string.mmk_path));
    if (!mmpkFile.exists()) {
      Snackbar.make(mMapView, String.format(getString(com.esri.arcgisruntime.demo.geocodeandroute.R.string.file_not_found), mmpkFile.getAbsolutePath()),
          Snackbar.LENGTH_SHORT).show();
      return null;
    } else {
      return mmpkFile.getAbsolutePath();
    }
  }


}
