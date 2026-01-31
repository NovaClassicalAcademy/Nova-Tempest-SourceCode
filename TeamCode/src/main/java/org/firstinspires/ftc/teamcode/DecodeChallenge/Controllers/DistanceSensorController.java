package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensorController {

    private DistanceSensor _distanceSensor;

    public DistanceSensorController(NormalizedColorSensor colorSensor) {
        _distanceSensor = (DistanceSensor) colorSensor;
    }

    public double GetDistanceCm(){
        return _distanceSensor.getDistance(DistanceUnit.CM);
    }

    public void DebugOutuput(Telemetry tele){
        tele.addData("Ball Dist: ", _distanceSensor.getDistance(DistanceUnit.INCH));
    }
}
