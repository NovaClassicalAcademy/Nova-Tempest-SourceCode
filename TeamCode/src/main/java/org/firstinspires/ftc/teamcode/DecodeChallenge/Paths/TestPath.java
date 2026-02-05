package org.firstinspires.ftc.teamcode.DecodeChallenge.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class TestPath {
    public PathChain Path1;
    public PathChain Path2;

    public TestPath(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(52.313, 115.067)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(52.313, 115.067),

                                new Pose(14.949, 47.750)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }
}
