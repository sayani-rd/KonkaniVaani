import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import '../models/word.dart';

class ApiClient {
  /// Chrome uses localhost; the Android emulator reaches your PC at 10.0.2.2.
  /// For a real phone, replace with http://<your-PC-IP>:8080
  static String get baseUrl {
    if (kIsWeb) return 'http://localhost:8080';
    if (defaultTargetPlatform == TargetPlatform.android) {
      return 'http://10.0.2.2:8080';
    }
    return 'http://localhost:8080';
  }

  Future<List<Word>> getWords() => _getList('/api/words');

  Future<List<Word>> search(String q, {String mode = 'contains'}) =>
      _getList('/api/words/search?q=${Uri.encodeQueryComponent(q)}&mode=$mode');

  Future<List<Word>> _getList(String path) async {
    final res = await http
        .get(Uri.parse('$baseUrl$path'))
        .timeout(const Duration(seconds: 15));
    // decode bytes as UTF-8 so Devanagari text displays correctly
    final body = jsonDecode(utf8.decode(res.bodyBytes)) as Map<String, dynamic>;
    if (res.statusCode != 200 || body['success'] != true) {
      throw Exception(body['message'] ?? 'Request failed (${res.statusCode})');
    }
    return (body['data'] as List)
        .map((j) => Word.fromJson(j as Map<String, dynamic>))
        .toList();
  }
}