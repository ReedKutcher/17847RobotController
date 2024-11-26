package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class AutoRight extends LinearOpMode {
    public class Arm{
        private DcMotorEx arm;

        public Arm(HardwareMap hardwareMap){
            arm = hardwareMap.get(DcMotorEx.class, "inarm");
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public class ExtendArm implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    arm.setPower(0.5);
                    initialized = true;
                }

                double pos = arm.getCurrentPosition();
                packet.put("armPos", pos);
                if (pos < 200.0) {
                    return true;
                } else {
                    arm.setPower(0);
                    return false;
                }
            }
        }
        public Action extendArm(){
            return new ExtendArm();
        }

        public class RetractArm implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    arm.setPower(-0.5);
                    initialized = true;
                }

                double pos = arm.getCurrentPosition();
                packet.put("armPos", pos);
                if (pos > 50.0) {
                    return true;
                } else {
                    arm.setPower(0);
                    return false;
                }
            }
        }
        public Action retractArm(){
            return new RetractArm();
        }
    }

    public class InServo{
        private Servo inServo;

        public InServo(HardwareMap hardwareMap){
            inServo = hardwareMap.get(Servo.class, "inwheel");
        }

        public class SpinIn implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                inServo.setPosition(1);
                return false;
            }
        }
        public Action spinIn(){
            return new SpinIn();
        }

        public class SpinStop implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                inServo.setPosition(0.5);
                return false;
            }
        }
        public Action spinStop(){
            return new SpinStop();
        }

        public class SpinOut implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                inServo.setPosition(0);
                return false;
            }
        }
        public Action spinOut(){
            return new SpinOut();
        }
    }

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(24, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Arm arm = new Arm(hardwareMap);
        InServo inServo = new InServo(hardwareMap);

        Action align1 = drive.actionBuilder(new Pose2d(24, -61, Math.toRadians(90)))
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(48, -48), Math.toRadians(90))
                .build();
        Action grab1 = drive.actionBuilder(new Pose2d(48, -48, Math.toRadians(90)))
                .setTangent(90)
                .lineToY(-40)
                .waitSeconds(3)
                .build();
        Action place1 = drive.actionBuilder(new Pose2d(48, -40, Math.toRadians(90)))
                .setTangent(Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(48, -48), Math.toRadians(270))
                .build();
        Action align2 = drive.actionBuilder(new Pose2d(48, -48, 270))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(40, -24, Math.toRadians(0)), Math.toRadians(0))
                .build();
        Action grab2 = drive.actionBuilder(new Pose2d(40, -24, 0))
                .setTangent(0)
                .lineToX(46)
                .build();
        Action place2 = drive.actionBuilder(new Pose2d(46, -24, 0))
                .setTangent(270)
                .strafeToLinearHeading(new Vector2d(48, -48), Math.toRadians(270))
                .build();
        Action align3 = drive.actionBuilder(new Pose2d(48, -48, 270))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(48, -24, Math.toRadians(0)), Math.toRadians(0))
                .build();
        Action grab3 = drive.actionBuilder(new Pose2d(48, -24, 0))
                .setTangent(0)
                .lineToX(56)
                .build();
        Action place3 = drive.actionBuilder(new Pose2d(56, -24, 0))
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(56, -48, Math.toRadians(270)), Math.toRadians(270))
                .build();
        Action park = drive.actionBuilder(new Pose2d(56, -48, 270))
                .setTangent(270)
                .lineToY(-56)
                .build();
        Action pause = drive.actionBuilder(new Pose2d(0, 0, 0))
                .waitSeconds(3)
                .build();

        Actions.runBlocking(
                new SequentialAction(
                        align1,
                        arm.extendArm(),
                        inServo.spinIn(),
                        grab1,
                        pause,
                        inServo.spinStop(),
                        place1,
                        inServo.spinOut(),
                        pause,
                        inServo.spinStop(),
                        arm.retractArm(),
                        align2,
                        arm.extendArm(),
                        inServo.spinIn(),
                        grab2,
                        pause,
                        inServo.spinStop(),
                        place2,
                        inServo.spinOut(),
                        pause,
                        inServo.spinStop(),
                        arm.retractArm(),
                        align3,
                        arm.extendArm(),
                        inServo.spinIn(),
                        grab3,
                        pause,
                        inServo.spinStop(),
                        place3,
                        inServo.spinOut(),
                        pause,
                        inServo.spinStop(),
                        arm.retractArm(),
                        park
                )
        );
    }
}
