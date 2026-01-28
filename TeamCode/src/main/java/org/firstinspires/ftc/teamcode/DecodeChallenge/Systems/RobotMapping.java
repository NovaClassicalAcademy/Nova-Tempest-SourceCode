package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class RobotMapping {
    public DcMotor FrontLeftDrive, FrontRightDrive, BackLeftDrive, BackRightDrive;
    public DcMotor YLeftEncoder, YRightEncoder, XEncoder;
    public CRServo LowerLeftIntake, LowerRightIntake, UpperLeftIntake, UpperRightIntake;
    public DcMotorEx Goat;
    public Servo Scooper;
    public NormalizedColorSensor ColorSensor;
    public WebcamName Webcam;

    private final HardwareMap _hardwareMap;

    public RobotMapping(HardwareMap hardwareMap) {
        if (hardwareMap == null){
            throw new RuntimeException("Hardware Map cannot be null");
        }

        _hardwareMap = hardwareMap;

        InitDriveMotors();
        InitEncoderMotors();
        InitIntakeMotors();
        InitGoatMotor();
        InitScooper();
        InitColorSensor();
        InitWebcam();
    }

    private void InitDriveMotors(){
        FrontLeftDrive = _hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = _hardwareMap.get(DcMotor.class, "FrontRight");
        BackLeftDrive = _hardwareMap.get(DcMotor.class, "BackLeft");
        BackRightDrive = _hardwareMap.get(DcMotor.class, "BackRight");

        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FrontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        FrontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        BackLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        BackRightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    private void InitEncoderMotors() {
        YLeftEncoder = _hardwareMap.get(DcMotor.class, "YLeftEncoder");
        YRightEncoder = _hardwareMap.get(DcMotor.class, "YRightEncoder");
        XEncoder = _hardwareMap.get(DcMotor.class, "XEncoder");

        YLeftEncoder.setDirection(DcMotor.Direction.REVERSE);
        YRightEncoder.setDirection(DcMotor.Direction.FORWARD);
        XEncoder.setDirection(DcMotor.Direction.FORWARD);
    }

    private void InitIntakeMotors() {
        LowerLeftIntake = _hardwareMap.get(CRServo.class, "IntakeLowerLeft");
        LowerRightIntake = _hardwareMap.get(CRServo.class, "IntakeLowerRight");
        UpperLeftIntake = _hardwareMap.get(CRServo.class, "IntakeUpperLeft");
        UpperRightIntake = _hardwareMap.get(CRServo.class, "IntakeUpperRight");

        LowerLeftIntake.setDirection(CRServo.Direction.FORWARD);
        LowerRightIntake.setDirection(CRServo.Direction.REVERSE);
        UpperLeftIntake.setDirection(CRServo.Direction.REVERSE);
        UpperRightIntake.setDirection(CRServo.Direction.REVERSE);
    }

    private void InitGoatMotor() {
        Goat = _hardwareMap.get(DcMotorEx.class, "Goat");
        Goat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Goat.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Goat.setDirection(DcMotor.Direction.REVERSE);
    }

    private void InitScooper() {
        Scooper = _hardwareMap.get(Servo.class, "scooper");
    }

    private void InitColorSensor() {
        ColorSensor = _hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
    }

    private void InitWebcam() {
        Webcam = _hardwareMap.get(WebcamName.class, "WebCam");
    }
}
