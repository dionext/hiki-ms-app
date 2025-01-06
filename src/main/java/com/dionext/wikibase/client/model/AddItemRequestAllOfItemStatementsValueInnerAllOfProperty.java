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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AddItemRequestAllOfItemStatementsValueInnerAllOfProperty
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class AddItemRequestAllOfItemStatementsValueInnerAllOfProperty {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  @javax.annotation.Nullable
  private String id;

  public static final String SERIALIZED_NAME_DATA_TYPE = "data_type";
  @SerializedName(SERIALIZED_NAME_DATA_TYPE)
  @javax.annotation.Nullable
  @JsonProperty("data_type")
  private String dataType;

  public AddItemRequestAllOfItemStatementsValueInnerAllOfProperty() {
  }

  public AddItemRequestAllOfItemStatementsValueInnerAllOfProperty(
     String dataType
  ) {
    this();
    this.dataType = dataType;
  }

  public AddItemRequestAllOfItemStatementsValueInnerAllOfProperty id(@javax.annotation.Nullable String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the Property
   * @return id
   */
  @javax.annotation.Nullable
  public String getId() {
    return id;
  }

  public void setId(@javax.annotation.Nullable String id) {
    this.id = id;
  }


  /**
   * The data type of the Property
   * @return dataType
   */
  @javax.annotation.Nullable
  public String getDataType() {
    return dataType;
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddItemRequestAllOfItemStatementsValueInnerAllOfProperty addItemRequestAllOfItemStatementsValueInnerAllOfProperty = (AddItemRequestAllOfItemStatementsValueInnerAllOfProperty) o;
    return Objects.equals(this.id, addItemRequestAllOfItemStatementsValueInnerAllOfProperty.id) &&
        Objects.equals(this.dataType, addItemRequestAllOfItemStatementsValueInnerAllOfProperty.dataType);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dataType);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddItemRequestAllOfItemStatementsValueInnerAllOfProperty {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("data_type");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to AddItemRequestAllOfItemStatementsValueInnerAllOfProperty
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AddItemRequestAllOfItemStatementsValueInnerAllOfProperty is not found in the empty JSON string", AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AddItemRequestAllOfItemStatementsValueInnerAllOfProperty` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("id") != null && !jsonObj.get("id").isJsonNull()) && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if ((jsonObj.get("data_type") != null && !jsonObj.get("data_type").isJsonNull()) && !jsonObj.get("data_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `data_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("data_type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AddItemRequestAllOfItemStatementsValueInnerAllOfProperty' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AddItemRequestAllOfItemStatementsValueInnerAllOfProperty> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.class));

       return (TypeAdapter<T>) new TypeAdapter<AddItemRequestAllOfItemStatementsValueInnerAllOfProperty>() {
           @Override
           public void write(JsonWriter out, AddItemRequestAllOfItemStatementsValueInnerAllOfProperty value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AddItemRequestAllOfItemStatementsValueInnerAllOfProperty read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of AddItemRequestAllOfItemStatementsValueInnerAllOfProperty given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of AddItemRequestAllOfItemStatementsValueInnerAllOfProperty
   * @throws IOException if the JSON string is invalid with respect to AddItemRequestAllOfItemStatementsValueInnerAllOfProperty
   */
  public static AddItemRequestAllOfItemStatementsValueInnerAllOfProperty fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.class);
  }

  /**
   * Convert an instance of AddItemRequestAllOfItemStatementsValueInnerAllOfProperty to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

