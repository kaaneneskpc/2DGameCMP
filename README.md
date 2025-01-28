# 2D Game with Compose Multiplatform

A cross-platform 2D game built using Kotlin Multiplatform and Compose Multiplatform, supporting Android, iOS, Desktop, and Web platforms.

## Getting Started

This project is a multiplatform game that demonstrates the power of Kotlin Multiplatform and Compose Multiplatform for game development. The game provides a unified codebase that runs across multiple platforms while maintaining platform-specific optimizations.

## App Contents

## Tech Stack

- **Kotlin Multiplatform**: For sharing code across platforms
- **Compose Multiplatform**: For UI development
- **Koin**: Dependency injection
- **Platform-specific Audio Implementations**: Custom audio handling for each platform
- **Gradle**: Build system
- **Kotlin Coroutines**: For asynchronous programming

## Architecture Details

The project follows a clean architecture approach with the following key components:

### Project Structure
```
composeApp/
├── commonMain/          # Shared code across all platforms
├── androidMain/         # Android-specific implementations
├── iosMain/            # iOS-specific implementations
├── desktopMain/        # Desktop-specific implementations
├── wasmJsMain/         # Web-specific implementations
```

### Key Components
- **Game Logic**: Centralized in commonMain for cross-platform consistency
- **Audio System**: Platform-specific implementations with a common interface
- **Settings Management**: Shared implementation with platform-specific storage
- **UI Components**: Built with Compose Multiplatform
- **Dependency Injection**: Managed through Koin with platform-specific modules

## Features

- Cross-platform gameplay experience
- Platform-specific audio handling
- Settings management
- Score tracking system
- Game status monitoring
- Modern UI with Compose Multiplatform
- Responsive design for different screen sizes

## Installation

### Prerequisites
- Android Studio Arctic Fox or newer
- Xcode (for iOS development)
- JDK 11 or newer
- Kotlin Multiplatform Mobile (KMM) plugin

### Build and Run

1. Clone the repository:
```bash
git clone https://github.com/yourusername/2DGameCMP.git
```

2. Open the project in Android Studio

3. For different platforms:
   - **Android**: Run the android configuration
   - **iOS**: Open the Xcode project in the iosApp directory
   - **Desktop**: Run the desktop configuration (Gradle -> Execute Gradle Task -> composeApp:run)
   - **Web**: Run the browser configuration (Gradle -> Execute Gradle Task -> wasmJsBrowserRun)

## Contribution Guide

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards
- Follow Kotlin coding conventions
- Maintain platform-specific code in appropriate source sets
- Include tests for new features
- Update documentation as needed

## Contact

**Kaan Enes Kapıcı**
- LinkedIn: [Kaan Enes Kapıcı](https://www.linkedin.com/in/kaaneneskpc/)
- GitHub: [@kaaneneskpc](https://github.com/kaaneneskpc)
- Email: kaaneneskpc1@gmail.com

Feel free to reach out if you have any questions or suggestions!

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you're sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.
