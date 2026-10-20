import 'package:flutter/material.dart';
import 'core/theme.dart';
import 'screens/splash_screen.dart'; 

// Firebase init is removed for now. The app only talks to the Java API.
// We add it back in the Auth phase, when users log in.
void main() {
  runApp(const KonkaniVaaniApp());
}

class KonkaniVaaniApp extends StatelessWidget {
  const KonkaniVaaniApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Konkani Vaani',
      debugShowCheckedModeBanner: false,
      theme: buildTheme(),
      home: const SplashScreen(),
    );
  }
}