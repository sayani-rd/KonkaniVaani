import 'package:flutter/material.dart';
import '../core/theme.dart';
import '../models/word.dart';

class WordDetailScreen extends StatelessWidget {
  final Word word;
  const WordDetailScreen({super.key, required this.word});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(word.romanized)),
      body: ListView(
        padding: const EdgeInsets.all(20),
        children: [
          Text(word.konkaniWord,
              style: const TextStyle(
                  fontSize: 40,
                  fontWeight: FontWeight.bold,
                  color: AppColors.primary)),
          const SizedBox(height: 4),
          Text(word.romanized,
              style: const TextStyle(fontSize: 20, color: AppColors.primaryLight)),
          const SizedBox(height: 12),
          Chip(label: Text(word.partOfSpeech.toLowerCase())),
          const SizedBox(height: 12),
          Text(word.meaning, style: const TextStyle(fontSize: 18)),
          const SizedBox(height: 24),
          const Text('Regional variations',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          if (word.variations.isEmpty) const Text('No variations yet.'),
          for (final v in word.variations)
            Card(
              color: Colors.white,
              child: ListTile(
                title: Text(v.writtenPronunciation),
                subtitle: Text(v.regionName),
                trailing: Icon(
                  v.audioUrl != null ? Icons.volume_up : Icons.volume_off,
                  color: AppColors.primaryLight,
                ),
              ),
            ),
          if (word.missingRegions.isNotEmpty) ...[
            const SizedBox(height: 24),
            const Text('Missing dialects (help add them!)',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Wrap(
              spacing: 8,
              children: word.missingRegions
                  .map((r) => Chip(label: Text(r)))
                  .toList(),
            ),
          ],
        ],
      ),
    );
  }
}