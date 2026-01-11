package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RPMManager {
    public enum RPMStatus {REACHED, RUNNING, TIMEOUT, LOW_BATTERY}
    private final DcMotorEx _motor;
    private final ElapsedTime _timer = new ElapsedTime();
    private final double _ticksPerRev;

    public RPMManager(DcMotorEx motor, double ticksPerRev) {
        _motor = motor;
        _ticksPerRev = ticksPerRev;

        _motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public RPMStatus Update(double targetRPM, double tolerance, double timeOutSeconds) {

        double targetTPS = (targetRPM/60.0) * _ticksPerRev;
        _motor.setVelocity(targetTPS);

        double currentRPM = (_motor.getVelocity()/_ticksPerRev) * 60.0;

        if (Math.abs(currentRPM - targetRPM) <= tolerance) {
            _timer.reset();
            return RPMStatus.REACHED;
        }

        if (_timer.seconds() > timeOutSeconds) {
            _timer.reset();
            return RPMStatus.TIMEOUT;
        }

        return RPMStatus.RUNNING;
    }
}
