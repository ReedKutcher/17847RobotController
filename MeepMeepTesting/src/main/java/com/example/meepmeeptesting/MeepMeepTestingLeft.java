package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingLeft {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setStartPose(new Pose2d(-24, -61, Math.toRadians(90)))
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-24, -61, Math.toRadians(90)))
                        .splineToConstantHeading(new Vector2d(-48, -48), Math.toRadians(90))
                        //extend intake
                        //turn on intake servo
                        .lineToY(-40)
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(270)), Math.toRadians(180))
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-40, -24, Math.toRadians(180)), Math.toRadians(90))
                        .setTangent(Math.toRadians(180))
                        .lineToX(-46)
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(270)), Math.toRadians(180))
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-48, -24, Math.toRadians(180)), Math.toRadians(180))
                        .lineToX(-56)
                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(270)), Math.toRadians(270))
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-36, -10, Math.toRadians(0)), Math.toRadians(0))
                        .lineToX(-26)
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}