//package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.AprilTagWebcam;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//@Autonomous(name = "AprilTagTest", group = "Test")
//public class AprilTagTest extends OpMode {
//    AprilTagWebcam _aprilTagWebcam = new AprilTagWebcam();
//
//    @Override
//    public void init() {
//        _aprilTagWebcam.Init(hardwareMap, telemetry);
//    }
//
//    @Override
//    public void loop() {
//        _aprilTagWebcam.Update();
//        AprilTagDetection id20 = _aprilTagWebcam.getTagByID(20);
//        _aprilTagWebcam.displayDetectionTelemetry(id20);
//    }
//}
