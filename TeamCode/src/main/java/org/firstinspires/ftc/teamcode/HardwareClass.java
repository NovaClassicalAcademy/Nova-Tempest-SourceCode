package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class HardwareClass {
    public DcMotor FrontLeftDrive = null;
    public DcMotor FrontRightDrive = null;
    public DcMotor BackLeftDrive = null;
    public DcMotor BackRightDrive = null;
    public DcMotor YLeftEncoder = null;
    public DcMotor YRightEncoder = null;
    public DcMotor XEncoder = null;
    public CRServo RouletteServo = null;
    public CRServo IntakeLowerLeft = null;
    public CRServo IntakeLowerRight = null;
    public CRServo IntakeUpperLeft = null;
    public CRServo IntakeUpperRight = null;
    public DcMotor ShooterMotor = null;
    public NormalizedColorSensor NormalizedColorSensor = null;
    public WebcamName WebCam = null;
    public IMU Imu = null;


    public void Init(HardwareMap hardwareMap){


        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        BackLeftDrive = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRightDrive = hardwareMap.get(DcMotor.class, "BackRight");


        Imu = hardwareMap.get(IMU.class, "imu");
        YLeftEncoder = hardwareMap.get(DcMotor.class, "YLeftEncoder");
        YRightEncoder = hardwareMap.get(DcMotor.class, "YRightEncoder");
        XEncoder = hardwareMap.get(DcMotor.class, "XEncoder");


        IntakeLowerLeft = hardwareMap.get(CRServo.class, "IntakeLowerLeft");
        IntakeLowerRight = hardwareMap.get(CRServo.class, "IntakeLowerRight");
        IntakeUpperLeft = hardwareMap.get(CRServo.class, "IntakeUpperLeft");
        IntakeUpperRight = hardwareMap.get(CRServo.class, "IntakeUpperRight");


        ShooterMotor = hardwareMap.get(DcMotor.class, "Goat");


        RouletteServo = hardwareMap.get(CRServo.class, "RouletteServo");//


        NormalizedColorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");


        WebCam = hardwareMap.get(WebcamName.class, "WebCam");


        FrontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        BackLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);


        YLeftEncoder.setDirection(DcMotorSimple.Direction.FORWARD);
        YRightEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
        XEncoder.setDirection(DcMotorSimple.Direction.REVERSE);


        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        YLeftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        YLeftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        YRightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        YRightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        XEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        XEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        IntakeLowerLeft.setDirection(CRServo.Direction.FORWARD); //spin normal (clock-wise)
        IntakeLowerRight.setDirection(CRServo.Direction.REVERSE); //spin reverse
        IntakeUpperLeft.setDirection(CRServo.Direction.REVERSE); //spin normal
        IntakeUpperRight.setDirection(CRServo.Direction.REVERSE); //spin reverse


        ShooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}

