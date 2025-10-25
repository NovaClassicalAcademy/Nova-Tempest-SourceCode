package org.firstinspires.ftc.teamcode.Config;
//PIDController.java calculates and corrects values. Does the math.
//Calculates how to get there and corrects. Keeps us on the path set by PedroPathing
public class PIDController {

    private double _kP; //proportional gain. how much power to react to error.
    private double _kI; //integral gain. how much to care about error.
    private double _kD; //derivative gain. how much to slow down to target.

    private double _integralSum = 0; //stores errors
    private double _integralSumLimit = 0;
    private double _lastError = 0; //what was the last error?
    private double _lastTime = 0; //when was the last error?
    public PIDController(double v, int i, double v1) {
    }
    ///This method calculates the error value.
    public double calculateError(double target, double current){
        double error = target - current;

        double currentTime = System.nanoTime() / 1e9;
        double deltaTime = currentTime - _lastTime;

        double proportional = _kP * error;

        _integralSum += error * deltaTime;
        double integral = _kI * _integralSum;

        double derivative = _kD * ((error - _lastError) / deltaTime);

        return proportional + integral + derivative;
    }
    public void reset() {
        _lastError = 0;
        _integralSum = 0;
        _lastTime = 0;
    }
    public void setPID(double kP, double kI, double kD) {
        _kP = kP;
        _kI = kI;
        _kD = kD;
    }
    public void setIntegralLimit(double limit) {
         _integralSumLimit = limit;
    }
}
