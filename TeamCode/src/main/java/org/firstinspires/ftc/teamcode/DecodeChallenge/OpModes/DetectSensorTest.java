package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.DistanceSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;

@Autonomous (name="DetectingTeset", group= "Tests")
public class DetectSensorTest extends OpMode {

    private DistanceSensorController _distanceRear;
    private DistanceSensorController _distanceFront;

    @Override
    public void init() {
        RobotMapping rb = new RobotMapping(hardwareMap);
        _distanceFront = new DistanceSensorController(rb.FrontColorSensor);
        _distanceRear = new DistanceSensorController(rb.RearColorSensor);
    }

    @Override
    public void loop() {
        telemetry.addData("Front: ", _distanceFront.GetDistanceInches());
        telemetry.addData("Rear: ", _distanceRear.GetDistanceInches());
        telemetry.update();
    }
}
