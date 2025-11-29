package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.bosch.BNO055IMU;

@TeleOp(name="Drive_Base_With_Intake_Test_Abhinav", group="Linear OpMode")
public class DriveBaseWithIntakeTestAbhinav extends LinearOpMode {

    private MecanumDrive drive;
    private CRServo sparkArm;
    private BNO055IMU imu;
    private double headingOffset = 0.0;

    @Override
    public void runOpMode() {

        sparkArm = hardwareMap.get(CRServo.class, "sparkArm");

        Motor frontLeft = new Motor(hardwareMap, "FrontLeft");
        Motor frontRight = new Motor(hardwareMap, "FrontRight");
        Motor backLeft = new Motor(hardwareMap, "BackLeft");
        Motor backRight = new Motor(hardwareMap, "BackRight");

        frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        frontLeft.setInverted(true);
        frontRight.setInverted(true);
        backLeft.setInverted(false);
        backRight.setInverted(true);

        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        telemetry.addLine("IMU Initialized. Press Start.");
        telemetry.update();

        waitForStart();

        double power;

        headingOffset = imu.getAngularOrientation().firstAngle;

        double headingDeg ,rawHeading;

        while (opModeIsActive()) {

            rawHeading = imu.getAngularOrientation().firstAngle;
            headingDeg = rawHeading - headingOffset;

            headingDeg = ((headingDeg + 180) % 360) - 180;

            double headingRad = Math.toRadians(headingDeg);

            drive.driveFieldCentric(
                    gamepad1.left_stick_x, //strafe
                    -gamepad1.left_stick_y,     // forward/back
                    gamepad1.right_stick_x,     // rotate
                    headingDeg, //must be in degrees
                    false
            );

//            drive.driveRobotCentric(
//                    gamepad1.left_stick_x,   // strafe left/right
//                    -gamepad1.left_stick_y,   // forward/back
//                    gamepad1.right_stick_x    // rotation
//            );

            power = gamepad1.right_trigger - gamepad1.left_trigger;

            sparkArm.setPower(power);

            if (gamepad1.y) {
                headingOffset = imu.getAngularOrientation().firstAngle; //reset
            }

            telemetry.addData("Heading (deg)", headingDeg);
//            telemetry.addData("Heading (rad)", headingRad);

            telemetry.addData("SPARKmini Power", power);
            telemetry.update();
        }
    }
}
