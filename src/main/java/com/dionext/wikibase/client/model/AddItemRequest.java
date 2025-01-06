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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AddItemRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class AddItemRequest {
  public static final String SERIALIZED_NAME_ITEM = "item";
  @SerializedName(SERIALIZED_NAME_ITEM)
  @javax.annotation.Nonnull
  private AddItemRequestAllOfItem item;

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

  public AddItemRequest() {
  }

  public AddItemRequest item(@javax.annotation.Nonnull AddItemRequestAllOfItem item) {
    this.item = item;
    return this;
  }

  /**
   * Get item
   * @return item
   */
  @javax.annotation.Nonnull
  public AddItemRequestAllOfItem getItem() {
    return item;
  }

  public void setItem(@javax.annotation.Nonnull AddItemRequestAllOfItem item) {
    this.item = item;
  }


  public AddItemRequest tags(@javax.annotation.Nullable List<String> tags) {
    this.tags = tags;
    return this;
  }

  public AddItemRequest addTagsItem(String tagsItem) {
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


  public AddItemRequest bot(@javax.annotation.Nullable Boolean bot) {
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


  public AddItemRequest comment(@javax.annotation.Nullable String comment) {
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
    AddItemRequest addItemRequest = (AddItemRequest) o;
    return Objects.equals(this.item, addItemRequest.item) &&
        Objects.equals(this.tags, addItemRequest.tags) &&
        Objects.equals(this.bot, addItemRequest.bot) &&
        Objects.equals(this.comment, addItemRequest.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, tags, bot, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddItemRequest {\n");
    sb.append("    item: ").append(toIndentedString(item)).append("\n");
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
    openapiFields.add("item");
    openapiFields.add("tags");
    openapiFields.add("bot");
    openapiFields.add("comment");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("item");
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to AddItemRequest
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AddItemRequest.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AddItemRequest is not found in the empty JSON string", AddItemRequest.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AddItemRequest.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AddItemRequest` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AddItemRequest.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the required field `item`
      AddItemRequestAllOfItem.validateJsonElement(jsonObj.get("item"));
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
       if (!AddItemRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AddItemRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AddItemRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AddItemRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<AddItemRequest>() {
           @Override
           public void write(JsonWriter out, AddItemRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AddItemRequest read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of AddItemRequest given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of AddItemRequest
   * @throws IOException if the JSON string is invalid with respect to AddItemRequest
   */
  public static AddItemRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AddItemRequest.class);
  }

  /**
   * Convert an instance of AddItemRequest to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

