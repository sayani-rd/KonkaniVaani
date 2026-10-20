import 'package:flutter/material.dart';

class AppColors {
  static const primary = Color(0xFF4B1A78);
  static const primaryLight = Color(0xFF7B4FA8);
  static const background = Color(0xFFFAF8F4);
  static const text = Color(0xFF1E1630);
}

ThemeData buildTheme() {
  return ThemeData(
    useMaterial3: true,
    colorScheme: ColorScheme.fromSeed(
      seedColor: AppColors.primary,
      primary: AppColors.primary,
    ),
    scaffoldBackgroundColor: AppColors.background,
    appBarTheme: const AppBarTheme(
      backgroundColor: AppColors.primary,
      foregroundColor: Colors.white,
      centerTitle: true,
    ),
  );
}