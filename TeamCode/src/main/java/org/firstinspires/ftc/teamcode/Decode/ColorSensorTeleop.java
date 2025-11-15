package org.firstinspires.ftc.teamcode.Decode;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ColorSensorTeleop {


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
       GREEN = > 0.040, > 0.1600, > 0.1200


       PURPLE =  > 0.1200, > 0.140, > 0.240
       */




        if(normRed > 0.040 && normGreen > 0.1600 && normBlue > 0.1200){
            return DetectedColor.GREEN;
        }
        else if(normRed  > 0.1200 && normGreen < 0.140 && normBlue > 0.240){
            return DetectedColor.PURPLE;
        }
        else{
            return  DetectedColor.UNKNOWN;
        }








    }












}
