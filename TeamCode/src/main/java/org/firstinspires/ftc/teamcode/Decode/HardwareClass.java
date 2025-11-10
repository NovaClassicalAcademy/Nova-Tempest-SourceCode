package org.firstinspires.ftc.teamcode.Decode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.IntakeServo;
import org.firstinspires.ftc.teamcode.Config.OutputServo;

public class HardwareClass {
    public DcMotor FrontLeftDrive = null;
    public DcMotor FrontRightDrive = null;
    public DcMotor BackLeftDrive = null;
    public DcMotor BackRightDrive = null;
    public DcMotor YLeftEncoder = null;
    public DcMotor YRightEncoder = null;
    public DcMotor XEncoder = null;
    public Servo RouletteServo = null;
    public Servo IntakeLowerLeft = null;
    public Servo IntakeLowerRight = null;
    public Servo IntakeUpperLeft = null;
    public Servo IntakeUpperRight = null;
    public Servo OutputServo = null;
    public NormalizedColorSensor NormalizedColorSensor = null;

    public void Init(HardwareMap hardwareMap){

        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        BackLeftDrive = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRightDrive = hardwareMap.get(DcMotor.class, "BackRight");

        YLeftEncoder = hardwareMap.get(DcMotor.class, "VertEncoder1");
        YRightEncoder = hardwareMap.get(DcMotor.class, "VertEncoder2");
        XEncoder = hardwareMap.get(DcMotor.class, "StrafeEncoder");

        IntakeLowerLeft = hardwareMap.get(Servo.class, "IntakeLowerLeft");
        IntakeLowerRight = hardwareMap.get(Servo.class, "IntakeLowerRight");

        OutputServo = hardwareMap.get(Servo.class, "Chuck Servo");

        RouletteServo = hardwareMap.get(Servo.class, "RouletteServo");

        NormalizedColorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");

        FrontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        YLeftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YLeftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        YRightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YRightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        XEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        XEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        IntakeLowerLeft.setDirection(Servo.Direction.FORWARD); //spin normal (clock-wise)
        IntakeLowerRight.setDirection(Servo.Direction.REVERSE); //spin reverse
        IntakeUpperLeft.setDirection(Servo.Direction.FORWARD); //spin normal
        IntakeUpperRight.setDirection(Servo.Direction.REVERSE); //spin reverse

        OutputServo.setDirection(Servo.Direction.REVERSE); //spin opposite way to chuck out.
    }
}
