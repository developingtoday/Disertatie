package com.activities;

import android.app.DialogFragment;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.abstracte.INotifier;
import com.listeners.LocationController;
import com.obj.SensorData;
import com.utils.DataResource;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 6/17/12
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalibrateDialogFragment extends DialogFragment  {

    EditText txtAltitude;
    TextView tvPressure,tvRefPressure;
    Button btnCalibrate,btnCancel;
    LocationController _controller;
    float _refHeight;
    float _refPressure;
    private INotifier<Float> _notifierPresure=new INotifier<Float>() {
        @Override
        public void notifyView(Float aFloat) {
            tvPressure.setText(Float.toString(aFloat));
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _controller=LocationController.getInstance(getActivity().getApplicationContext());
        _controller.setNotifierPressure(_notifierPresure);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calibrateheight,container,false);
        View v=view.findViewById(R.id.layoutCalibrate);
        getDialog().setTitle("Calibration Height");
        getDialog().setCanceledOnTouchOutside(true);
        btnCalibrate=(Button)v.findViewById(R.id.btnCalibrate);
        btnCancel=(Button)v.findViewById(R.id.btnCancel);
        tvPressure=(TextView)v.findViewById(R.id.txtCalibratePressure);
        txtAltitude=(EditText)v.findViewById(R.id.txtCalibrateAltitude);
        txtAltitude.setText(Float.toString(DataResource.REF_HEIGHT));
        tvRefPressure=(TextView)v.findViewById(R.id.tvRefPressure);
        tvRefPressure.setText(Float.toString(DataResource.REF_PRESSURE));
        btnCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                   _refHeight=Float.parseFloat(txtAltitude.getText().toString());
                   _refPressure=Float.parseFloat(tvPressure.getText().toString());

                }catch (NumberFormatException ex)
                {
                   _refHeight=0;
                    _refPressure= SensorManager.PRESSURE_STANDARD_ATMOSPHERE;
                }

                DataResource.REF_HEIGHT=_refHeight;
                DataResource.REF_PRESSURE=_refPressure;
                getDialog().dismiss();
            }
        });
        btnCancel=(Button)v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      getDialog().dismiss();
                    }
                }
        );
        return v;
    }

    private void CloseDialog()
    {

    }

}
