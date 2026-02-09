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

    public LaunchController(DcMotorEx motor) {
        _motor = motor;

        _rpmTolerance = Range.clip(_targetVelocity * 0.1, 0, 50);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void StartVelocity(double targetVelocity)
    {
        _targetVelocity = targetVelocity;
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

    private double rpmToTicksPerSecond(double rpm, int ticksPerRev, double gearRatio) {
        // rpm -> ticks/sec
        return (rpm / 60.0) * ticksPerRev * gearRatio;
    }

    private double ticksPerSecondToRpm(double ticksPerSecond, int ticksPerRev, double gearRatio) {
        return (ticksPerSecond / (ticksPerRev * gearRatio)) * 60.0;
    }
}
