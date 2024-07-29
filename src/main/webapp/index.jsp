<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New User</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #eceff1;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            position: relative; /* Added for positioning the error message */
        }

        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            max-width: 400px;
            width: 100%;
        }

        .form-container label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }

        .form-container input[type="text"],
        .form-container input[type="email"],
        .form-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .form-container input[type="submit"] {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            box-sizing: border-box;
        }

        .form-container input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            text-decoration: none;
            font-size: 24px;
            color: #333;
            display: flex;
            align-items: center;
        }

        .back-button::before {
            content: '<';
            margin-right: 10px;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin: 10px auto 0; /* Centered horizontally with top margin */
            width: fit-content; /* Adjust width based on content */
            text-align: center; /* Center text */
            position: absolute;
            top: 0; /* Adjust top position */
            left: 50%; /* Center horizontally */
            transform: translateX(-50%); /* Center horizontally */
        }

        .register-container {
            margin-top: 20px;
            text-align: center;
        }

        .register-container p {
            margin: 0;
            font-size: 14px;
            color: #333;
        }

        .register-container a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        .register-container a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="form-container">
    <form method="POST" action="/people/login">
        <label for="email">Email</label>
        <input name="email" type="email" id="email" required>

        <label for="password">Password</label>
        <input name="password" type="password" id="password" required>

        <input type="submit" value="Login">
    </form>
    <div class="register-container">
        <p>Don't have an account?</p>
        <a href="/people/new">Register here</a>
    </div>
</div>

</body>
</html>
