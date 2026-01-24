package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Decode.HardwareClass;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebcam {
    private AprilTagProcessor _aprilTagProcessor;
    private WebcamName _webCam;
    private VisionPortal _visionPortal;
    private List<AprilTagDetection> _detectionTags = new ArrayList<>();

    private Telemetry _telemetry;

    public void AprilTagWebcam(WebcamName webCam, Telemetry telemetry){
        _telemetry = telemetry;
        _webCam = webCam;

        _aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.MM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder _builder = new VisionPortal.Builder();
        _builder.setCameraResolution(new Size(640, 480));
        _builder.addProcessor(_aprilTagProcessor);

        _visionPortal = _builder.build();
    }

    public void Update(){
        _detectionTags = _aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> _getDetectedTags(){
        return _detectionTags;
    }

    public void displayDetectionTelemetry(AprilTagDetection detectedID){
        if (detectedID == null) {
            return;
        }
        if (detectedID.metadata != null) {
            _telemetry.addLine(String.format("\n==== (ID %d) %s", detectedID.id, detectedID.metadata.name));
            _telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detectedID.ftcPose.x, detectedID.ftcPose.y, detectedID.ftcPose.z));
            _telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedID.ftcPose.pitch, detectedID.ftcPose.roll, detectedID.ftcPose.yaw));
            _telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detectedID.ftcPose.range, detectedID.ftcPose.bearing, detectedID.ftcPose.elevation));
        } else {
            _telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedID.id));
            _telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedID.center.x, detectedID.center.y));
        }
    }

    public AprilTagDetection getTagByID(int id){
        for (AprilTagDetection detection : _detectionTags){
            if (detection.id == id){
                return detection;
            }
        }

        return null;
    }

    public void stop(){
        if (_visionPortal != null){
            _visionPortal.close();
        }
    }
}