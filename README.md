# 2D Game with Compose Multiplatform

A cross-platform 2D game built using Kotlin Multiplatform and Compose Multiplatform, supporting Android, iOS, Desktop, and Web platforms.

## Getting Started

This project is a multiplatform game that demonstrates the power of Kotlin Multiplatform and Compose Multiplatform for game development. The game provides a unified codebase that runs across multiple platforms while maintaining platform-specific optimizations.

## App Contents

### Android

https://github.com/user-attachments/assets/617f5172-ddf5-45a3-931b-4f35130ed561

### IOS

https://github.com/user-attachments/assets/a2c88d2c-6930-4e96-997e-ae1cfa0eb4db

### Desktop

https://github.com/user-attachments/assets/e8c8bbc6-f060-45af-be96-9da1e9a796c0

### Web

https://github.com/user-attachments/assets/907d62f4-32cc-4354-91fe-b83dc2b00d44


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
