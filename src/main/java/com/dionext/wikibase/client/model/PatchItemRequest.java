/*
 * Wikibase REST API
 * OpenAPI definition of Wikibase REST API
 *
 * The version of the OpenAPI document: 1.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.dionext.wikibase.client.model;

import java.util.Objects;

import com.dionext.wikibase.client.JSON;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * PatchItemRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class PatchItemRequest {
  public static final String SERIALIZED_NAME_PATCH = "patch";
  @SerializedName(SERIALIZED_NAME_PATCH)
  @javax.annotation.Nonnull
  private List<PatchItemRequestAllOfPatchInner> patch = new ArrayList<>();

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  @javax.annotation.Nullable
  private List<String> tags = new ArrayList<>();

  public static final String SERIALIZED_NAME_BOT = "bot";
  @SerializedName(SERIALIZED_NAME_BOT)
  @javax.annotation.Nullable
  private Boolean bot = false;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  @javax.annotation.Nullable
  private String comment;

  public PatchItemRequest() {
  }

  public PatchItemRequest patch(@javax.annotation.Nonnull List<PatchItemRequestAllOfPatchInner> patch) {
    this.patch = patch;
    return this;
  }

  public PatchItemRequest addPatchItem(PatchItemRequestAllOfPatchInner patchItem) {
    if (this.patch == null) {
      this.patch = new ArrayList<>();
    }
    this.patch.add(patchItem);
    return this;
  }

  /**
   * A JSON Patch document as defined by RFC 6902
   * @return patch
   */
  @javax.annotation.Nonnull
  public List<PatchItemRequestAllOfPatchInner> getPatch() {
    return patch;
  }

  public void setPatch(@javax.annotation.Nonnull List<PatchItemRequestAllOfPatchInner> patch) {
    this.patch = patch;
  }


  public PatchItemRequest tags(@javax.annotation.Nullable List<String> tags) {
    this.tags = tags;
    return this;
  }

  public PatchItemRequest addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
   */
  @javax.annotation.Nullable
  public List<String> getTags() {
    return tags;
  }

  public void setTags(@javax.annotation.Nullable List<String> tags) {
    this.tags = tags;
  }


  public PatchItemRequest bot(@javax.annotation.Nullable Boolean bot) {
    this.bot = bot;
    return this;
  }

  /**
   * Get bot
   * @return bot
   */
  @javax.annotation.Nullable
  public Boolean getBot() {
    return bot;
  }

  public void setBot(@javax.annotation.Nullable Boolean bot) {
    this.bot = bot;
  }


  public PatchItemRequest comment(@javax.annotation.Nullable String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
   */
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }

  public void setComment(@javax.annotation.Nullable String comment) {
    this.comment = comment;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PatchItemRequest patchItemRequest = (PatchItemRequest) o;
    return Objects.equals(this.patch, patchItemRequest.patch) &&
        Objects.equals(this.tags, patchItemRequest.tags) &&
        Objects.equals(this.bot, patchItemRequest.bot) &&
        Objects.equals(this.comment, patchItemRequest.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patch, tags, bot, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PatchItemRequest {\n");
    sb.append("    patch: ").append(toIndentedString(patch)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    bot: ").append(toIndentedString(bot)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("patch");
    openapiFields.add("tags");
    openapiFields.add("bot");
    openapiFields.add("comment");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("patch");
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to PatchItemRequest
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!PatchItemRequest.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in PatchItemRequest is not found in the empty JSON string", PatchItemRequest.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!PatchItemRequest.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PatchItemRequest` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : PatchItemRequest.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the json data is an array
      if (!jsonObj.get("patch").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `patch` to be an array in the JSON string but got `%s`", jsonObj.get("patch").toString()));
      }

      JsonArray jsonArraypatch = jsonObj.getAsJsonArray("patch");
      // validate the required field `patch` (array)
      for (int i = 0; i < jsonArraypatch.size(); i++) {
        PatchItemRequestAllOfPatchInner.validateJsonElement(jsonArraypatch.get(i));
      };
      // ensure the optional json data is an array if present
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonNull() && !jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PatchItemRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PatchItemRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PatchItemRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PatchItemRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<PatchItemRequest>() {
           @Override
           public void write(JsonWriter out, PatchItemRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PatchItemRequest read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of PatchItemRequest given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of PatchItemRequest
   * @throws IOException if the JSON string is invalid with respect to PatchItemRequest
   */
  public static PatchItemRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PatchItemRequest.class);
  }

  /**
   * Convert an instance of PatchItemRequest to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
