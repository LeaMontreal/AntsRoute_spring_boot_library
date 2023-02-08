package com.lijun.springbootlibrary.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// TODO S20 21 create class ExtractJWT to extract user’s email from request header.
public class ExtractJWT {

  // TODO S20 22 implement payload extraction function
  public static String payloadJWTExtraction(String token, String extraction) {

    token.replace("Bearer ", "");

    // each part of jwt separated by a period “.” mark, after split, we’ll get three elements corresponds to 3 parts of jwt: header, payload, signature.
    String[] chunks = token.split("\\.");
    // decode the payload with a Base64.Decoder
    Base64.Decoder decoder = Base64.getUrlDecoder();

    // chunks[1] is payload
    String payload = new String(decoder.decode(chunks[1]));

    // get all entries of payload, save all entries into map
    String[] entries = payload.split(",");
    Map<String, String> map = new HashMap<String, String>();

    // find the entry named “sub” (the name include two double quote)
    for (String entry : entries) {
      String[] keyValue = entry.split(":");
      if (keyValue[0].equals(extraction)) {

        // remove useless “}” following with the user’s email.
        int remove = 1;
        if (keyValue[1].endsWith("}")) {
          remove = 2;
        }
        keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
        keyValue[1] = keyValue[1].substring(1);

        map.put(keyValue[0], keyValue[1]);
      }
    }
    if (map.containsKey(extraction)) {
      return map.get(extraction);
    }
    return null;
  }
}
