package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="DRIVE_BASE_TEST_ABHINAV", group="Linear OpMode")
public class DriveBaseTestAbhinav extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        Motor frontLeft = new Motor(hardwareMap, "FrontLeft");
        Motor frontRight = new Motor(hardwareMap, "FrontRight");
        Motor backLeft = new Motor(hardwareMap, "BackLeft");
        Motor backRight = new Motor(hardwareMap, "BackRight");

        frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        // ✅ Typical mecanum configuration:
        frontLeft.setInverted(true);
        frontRight.setInverted(true);
        backLeft.setInverted(true);
        backRight.setInverted(true);

        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        while (opModeIsActive()) {
            drive.driveRobotCentric(
                    gamepad1.left_stick_x,   // strafe left/right
                    -gamepad1.left_stick_y,   // forward/back
                    gamepad1.right_stick_x    // rotation
            );
        }
    }
}
