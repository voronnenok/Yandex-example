package com.example.yandexmap;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.yandexmap.api.TasksService;
import com.example.yandexmap.entity.Location;
import com.example.yandexmap.entity.Task;
import com.example.yandexmap.entity.Tasks;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;


/**
 * A placeholder fragment containing a simple view.
 */
public class MapFragment extends Fragment implements Callback<Tasks>{
    public static final String TAG = MapFragment.class.getSimpleName();
    private MapApplication appDelegate;
    private static TaskClickListener taskClickListener;
    private MapView mapView;
    private MapController mMapController;
    private OverlayManager mOverlayManager;
    private Overlay overlay;
    private ProgressBar progressBar;
    private static final String ARG_TASKS = "arg_tasks";
    private Tasks tasks;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        mapView = (MapView)view.findViewById(R.id.map);
        mMapController = mapView.getMapController();
        mOverlayManager = mMapController.getOverlayManager();
        // Disable determining the user's location
        mOverlayManager.getMyLocation().setEnabled(false);
        mapView.showZoomButtons(true);
        overlay = new Overlay(mMapController);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        view.findViewById(R.id.zoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                requestTasks();
            }
        });
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null) {
            requestTasks();
        } else {
            tasks = savedInstanceState.getParcelable(ARG_TASKS);
            showTasks(tasks);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskClickListener = ((TaskClickListener)activity);
        appDelegate = ((MapApplication)activity.getApplication());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskClickListener = null;
        appDelegate = null;
    }

    private void showTasks(Tasks tasks) {
        if(tasks != null) {
            for (Task task : tasks.getTasks()) {
                showTask(task);
            }
        }
    }

    private void requestTasks() {
        getTasksService().getTasks(this);
    }

    private TasksService getTasksService() {
        return appDelegate != null ? appDelegate.getTasksService() : null;
    }

    @Override
    public void success(Tasks tasks, Response response) {
        progressBar.setVisibility(View.GONE);
        this.tasks = tasks;
        showTasks(tasks);
        setZoomSpan();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_TASKS, tasks);
    }

    private void showTask(Task task) {
        // Load required resources
        Resources res = getResources();

        // Create an object for the layer
        Location taskLocation = task.getLocation();

        final OverlayItem taskOverlay = new OverlayItem(new GeoPoint(taskLocation.getLatitude() , taskLocation.getLongitude()), res.getDrawable(R.drawable.shop));
        // Create a balloon model for the object
        BalloonItem balloonTask = new BalloonItem(appDelegate, taskOverlay.getGeoPoint());
        balloonTask.setText(task.getTitle());
//        // Add the balloon model to the object
        taskOverlay.setBalloonItem(balloonTask);
        // Add the object to the layer
        overlay.addOverlayItem(taskOverlay);

        // Add the layer to the map
        mOverlayManager.addOverlay(overlay);
        balloonTask.setOnBalloonListener(new TaskBalloonClickListener(task));

    }

    @Override
    public void failure(RetrofitError error) {
        progressBar.setVisibility(View.GONE);
        Log.d("TasksResponse", "Tasks error " + error.getMessage());
    }

    private static void showTaskInfo(Task task) {
        if(taskClickListener != null) {
            taskClickListener.onTaskClick(task);
        }
    }

    private void setZoomSpan(){
        List<OverlayItem> list = overlay.getOverlayItems();
        double maxLat, minLat, maxLon, minLon;
        maxLat = maxLon = Double.MIN_VALUE;
        minLat = minLon = Double.MAX_VALUE;
        for (int i = 0; i < list.size(); i++){
            GeoPoint geoPoint = list.get(i).getGeoPoint();
            double lat = geoPoint.getLat();
            double lon = geoPoint.getLon();

            maxLat = Math.max(lat, maxLat);
            minLat = Math.min(lat, minLat);
            maxLon = Math.max(lon, maxLon);
            minLon = Math.min(lon, minLon);
        }
        mMapController.setZoomToSpan(maxLat - minLat, maxLon - minLon);
        GeoPoint an = new GeoPoint((maxLat + minLat) / 2, (maxLon + minLon) / 2);
        mMapController.setPositionAnimationTo(an);
    }


    private static class TaskBalloonClickListener implements OnBalloonListener{
        private final Task task;

        public TaskBalloonClickListener(Task task) {
            this.task = task;
        }

        @Override
        public void onBalloonViewClick(BalloonItem balloonItem, View view) {
            showTaskInfo(task);
        }
        @Override
        public void onBalloonShow(BalloonItem balloonItem) {}
        @Override
        public void onBalloonHide(BalloonItem balloonItem) {}
        @Override
        public void onBalloonAnimationStart(BalloonItem balloonItem) {}
        @Override
        public void onBalloonAnimationEnd(BalloonItem balloonItem) {}
    }
}
