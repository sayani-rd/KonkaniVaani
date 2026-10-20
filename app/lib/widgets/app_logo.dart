import 'package:flutter/material.dart';
import '../core/theme.dart';

/// Shows the logo cropped to just the text (the source image has large empty margins).
/// [height] is the visible height of the logo.
class AppLogo extends StatelessWidget {
  final double height;
  const AppLogo({super.key, required this.height});

  @override
  Widget build(BuildContext context) {
    // The text occupies roughly 46% of the image width and 30% of its height.
    final fullH = height / 0.30;
    final fullW = fullH * 1408 / 768;
    final visibleW = height * 650 / 230;

    return SizedBox(
      width: visibleW,
      height: height,
      child: ClipRect(
        child: OverflowBox(
          maxWidth: fullW,
          maxHeight: fullH,
          child: Image.asset(
            'assets/images/logo.png',
            width: fullW,
            height: fullH,
            fit: BoxFit.fill,
            // Multiply blends the off-white paper background into the app background
            color: AppColors.background,
            colorBlendMode: BlendMode.multiply,
          ),
        ),
      ),
    );
  }
}