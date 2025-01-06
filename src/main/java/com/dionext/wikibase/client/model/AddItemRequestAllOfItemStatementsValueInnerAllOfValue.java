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
import com.google.gson.annotations.JsonAdapter;
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
 * AddItemRequestAllOfItemStatementsValueInnerAllOfValue
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class AddItemRequestAllOfItemStatementsValueInnerAllOfValue {
  public static final String SERIALIZED_NAME_CONTENT = "content";
  @SerializedName(SERIALIZED_NAME_CONTENT)
  @javax.annotation.Nullable
  private Object content = null;

  /**
   * The value type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    @JsonProperty("value")
    VALUE("value"),

    @JsonProperty("somevalue")
    SOMEVALUE("somevalue"),

    @JsonProperty("novalue")
    NOVALUE("novalue");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TypeEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      TypeEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  @javax.annotation.Nullable
  private TypeEnum type;

  public AddItemRequestAllOfItemStatementsValueInnerAllOfValue() {
  }

  public AddItemRequestAllOfItemStatementsValueInnerAllOfValue content(@javax.annotation.Nullable Object content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   */
  @javax.annotation.Nullable
  public Object getContent() {
    return content;
  }

  public void setContent(@javax.annotation.Nullable Object content) {
    this.content = content;
  }


  public AddItemRequestAllOfItemStatementsValueInnerAllOfValue type(@javax.annotation.Nullable TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The value type
   * @return type
   */
  @javax.annotation.Nullable
  public TypeEnum getType() {
    return type;
  }

  public void setType(@javax.annotation.Nullable TypeEnum type) {
    this.type = type;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddItemRequestAllOfItemStatementsValueInnerAllOfValue addItemRequestAllOfItemStatementsValueInnerAllOfValue = (AddItemRequestAllOfItemStatementsValueInnerAllOfValue) o;
    return Objects.equals(this.content, addItemRequestAllOfItemStatementsValueInnerAllOfValue.content) &&
        Objects.equals(this.type, addItemRequestAllOfItemStatementsValueInnerAllOfValue.type);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, type);
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
    sb.append("class AddItemRequestAllOfItemStatementsValueInnerAllOfValue {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
    openapiFields.add("content");
    openapiFields.add("type");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to AddItemRequestAllOfItemStatementsValueInnerAllOfValue
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AddItemRequestAllOfItemStatementsValueInnerAllOfValue.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AddItemRequestAllOfItemStatementsValueInnerAllOfValue is not found in the empty JSON string", AddItemRequestAllOfItemStatementsValueInnerAllOfValue.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AddItemRequestAllOfItemStatementsValueInnerAllOfValue.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AddItemRequestAllOfItemStatementsValueInnerAllOfValue` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) && !jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      // validate the optional field `type`
      if (jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) {
        TypeEnum.validateJsonElement(jsonObj.get("type"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AddItemRequestAllOfItemStatementsValueInnerAllOfValue.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AddItemRequestAllOfItemStatementsValueInnerAllOfValue' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AddItemRequestAllOfItemStatementsValueInnerAllOfValue> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AddItemRequestAllOfItemStatementsValueInnerAllOfValue.class));

       return (TypeAdapter<T>) new TypeAdapter<AddItemRequestAllOfItemStatementsValueInnerAllOfValue>() {
           @Override
           public void write(JsonWriter out, AddItemRequestAllOfItemStatementsValueInnerAllOfValue value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AddItemRequestAllOfItemStatementsValueInnerAllOfValue read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of AddItemRequestAllOfItemStatementsValueInnerAllOfValue given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of AddItemRequestAllOfItemStatementsValueInnerAllOfValue
   * @throws IOException if the JSON string is invalid with respect to AddItemRequestAllOfItemStatementsValueInnerAllOfValue
   */
  public static AddItemRequestAllOfItemStatementsValueInnerAllOfValue fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AddItemRequestAllOfItemStatementsValueInnerAllOfValue.class);
  }

  /**
   * Convert an instance of AddItemRequestAllOfItemStatementsValueInnerAllOfValue to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
