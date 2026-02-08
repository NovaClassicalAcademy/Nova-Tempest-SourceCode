package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensor {

    NormalizedColorSensor colorSensor;


    public enum DetectedColor{
        GREEN,
        PURPLE,
        UNKNOWN,
    }

    public void init(HardwareMap hwMap){
        colorSensor = hwMap.get(NormalizedColorSensor.class, "ColorSensor");
        colorSensor.setGain(5);
    }



    public DetectedColor getDetectedColor(Telemetry telemetry){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        float normRed, normGreen, normBlue;
        normRed = colors.red;
        normGreen = colors.green;
        normBlue = colors.blue;



        telemetry.addData("red",normRed);
        telemetry.addData("green", normGreen);
        telemetry.addData("blue", normBlue);




        //TODO might wanna adjust gain or comment /alpha to focus on closer ball

        /*
        TODO not right needa fix
                red, green, blue
        GREEN = > 0.0020, > 0.0058, > 0.0047

        PURPLE =  > 0.0035, > 0.0050, > 0.0065
        */


        if((normRed > 0.0018 && normRed < 0.0022) && (normGreen > 0.0056 && normGreen < 0.0061) && (normBlue > 0.0043 && normBlue < 0.0050)){
            return DetectedColor.GREEN;
        }
        else if((normRed > 0.0032 && normRed < 0.0038) && (normGreen > 0.0047 && normGreen < 0.0053) && (normBlue > 0.0062 && normBlue < 0.0068)){
            return DetectedColor.PURPLE;
        }
        else{
            return  DetectedColor.UNKNOWN;
        }



    }






}
