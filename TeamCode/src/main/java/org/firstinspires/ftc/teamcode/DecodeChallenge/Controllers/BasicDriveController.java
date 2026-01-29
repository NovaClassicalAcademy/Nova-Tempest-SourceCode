//package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
//
//public class BasicDriveController {
//    private RobotMapping _robotMapping;
//    public DcMotor _leftFrontDrive;
//    public DcMotor _rightFrontDrive;
//    public DcMotor _leftBackDrive;
//    public DcMotor _rightBackDrive;
//    public DcMotor _yLeftEncoder;
//    private int TicksPerRev = 2000;
//    private double EncoderWheelDiameter = 48;
//    private double _circumference = (EncoderWheelDiameter * Math.PI);
//    private double TicksPerMm = TicksPerRev / _circumference;
//    private double _maxPower = -0.15;
//    private int _targetTicks;
//
//
//    public BasicDriveController(RobotMapping rbtMap) {
//        _robotMapping = rbtMap;
//
//        _leftFrontDrive = _robotMapping.FrontLeftDrive;
//        _rightFrontDrive = _robotMapping.FrontRightDrive;
//        _leftBackDrive = _robotMapping.BackLeftDrive;
//        _rightBackDrive = _robotMapping.BackRightDrive;
//
//        _yLeftEncoder = _robotMapping.YLeftEncoder;
//    }
//    public void SetTarget(double millimeters) {
//        _targetTicks = (int)(millimeters * TicksPerMm);
//        _yLeftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        setAllMotorPowers(_maxPower);
//    }
//
//    public boolean IsDoneMoving(){
//        if (Math.abs(_yLeftEncoder.getCurrentPosition()) < Math.abs(_targetTicks)){
//            return false;
//        }
//
//        _targetTicks = -1; //resets ticker
//        setAllMotorPowers(0);
//        return true;
//    }
//
//    public void DebugOutput(Telemetry telemetry){
//        telemetry.addData("Ticks: ", TicksPerMm);
//        telemetry.addData("Circumference: ", _circumference);
//        telemetry.addData("Current Power: ", _leftBackDrive.getPower());
//        telemetry.addData("Current Y Encoder: ", _yLeftEncoder.getCurrentPosition());
//        telemetry.addData("Target Ticks: ", _targetTicks);
//    }
//    private void setAllMotorPowers(double power){
//        _leftFrontDrive.setPower(power);
//        _rightFrontDrive.setPower(power);
//        _leftBackDrive.setPower(power);
//        _rightBackDrive.setPower(power);
//    }
//}