class Variation {
  final String id;
  final String region;
  final String regionName;
  final String writtenPronunciation;
  final String? audioUrl;

  Variation({
    required this.id,
    required this.region,
    required this.regionName,
    required this.writtenPronunciation,
    this.audioUrl,
  });

  factory Variation.fromJson(Map<String, dynamic> j) => Variation(
        id: j['id'] ?? '',
        region: j['region'] ?? '',
        regionName: j['regionName'] ?? '',
        writtenPronunciation: j['writtenPronunciation'] ?? '',
        audioUrl: j['audioUrl'],
      );
}

class Word {
  final String id;
  final String konkaniWord;
  final String romanized;
  final String meaning;
  final String partOfSpeech;
  final List<Variation> variations;
  final List<String> missingRegions;

  Word({
    required this.id,
    required this.konkaniWord,
    required this.romanized,
    required this.meaning,
    required this.partOfSpeech,
    required this.variations,
    required this.missingRegions,
  });

  factory Word.fromJson(Map<String, dynamic> j) => Word(
        id: j['id'] ?? '',
        konkaniWord: j['konkaniWord'] ?? '',
        romanized: j['romanized'] ?? '',
        meaning: j['meaning'] ?? '',
        partOfSpeech: j['partOfSpeech'] ?? '',
        variations: (j['variations'] as List? ?? [])
            .map((v) => Variation.fromJson(v as Map<String, dynamic>))
            .toList(),
        missingRegions: (j['missingRegions'] as List? ?? []).cast<String>(),
      );
}