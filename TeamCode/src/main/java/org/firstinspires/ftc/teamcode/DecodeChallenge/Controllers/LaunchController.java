package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LaunchController {

    private final DcMotorEx _motor;
    private double _rpmTolerance;
    private double _targetVelocity;
    private final ElapsedTime _timer = new ElapsedTime();

    public LaunchController(DcMotorEx motor, double targetVelocity) {
        _motor = motor;

        _targetVelocity = targetVelocity;
        _rpmTolerance = Range.clip(_targetVelocity * 0.1, 0, 50);

        PIDFCoefficients newCoefficients = new PIDFCoefficients(10.0, 0.0, 0.1, ((double) 32767 / _targetVelocity));
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, newCoefficients);
    }
    public void StartVelocity()
    {
        _motor.setVelocity(_targetVelocity);
    }

    public void Stop() {
        _motor.setVelocity(0.0);
    }

    public boolean IsAtFullSpeed(){
        double currentVelocity = _motor.getVelocity();
        return Math.abs(_targetVelocity - currentVelocity) < _rpmTolerance;
    }

    public void ReportVelocity(Telemetry telemetry){
        telemetry.addData("Goat Speed", _motor.getVelocity());
        telemetry.addData("Goat Power", _motor.getPower());
    }
}
