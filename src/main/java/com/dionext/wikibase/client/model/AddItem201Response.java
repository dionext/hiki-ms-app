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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Set;

/**
 * AddItem201Response
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class AddItem201Response {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  @javax.annotation.Nonnull
  private String id;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    @JsonProperty("item")
    ITEM("item");

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
  @javax.annotation.Nonnull
  private TypeEnum type;

  public static final String SERIALIZED_NAME_LABELS = "labels";
  @SerializedName(SERIALIZED_NAME_LABELS)
  @javax.annotation.Nonnull
  private Map<String, String> labels = new HashMap<>();

  public static final String SERIALIZED_NAME_DESCRIPTIONS = "descriptions";
  @SerializedName(SERIALIZED_NAME_DESCRIPTIONS)
  @javax.annotation.Nonnull
  private Map<String, String> descriptions = new HashMap<>();

  public static final String SERIALIZED_NAME_ALIASES = "aliases";
  @SerializedName(SERIALIZED_NAME_ALIASES)
  @javax.annotation.Nonnull
  private Map<String, List<String>> aliases = new HashMap<>();

  public static final String SERIALIZED_NAME_SITELINKS = "sitelinks";
  @SerializedName(SERIALIZED_NAME_SITELINKS)
  @javax.annotation.Nullable
  private Map<String, Object> sitelinks;

  public static final String SERIALIZED_NAME_STATEMENTS = "statements";
  @SerializedName(SERIALIZED_NAME_STATEMENTS)
  @javax.annotation.Nullable
  private Map<String, Object> statements;

  public AddItem201Response() {
  }

  public AddItem201Response(
     String id, 
     TypeEnum type
  ) {
    this();
    this.id = id;
    this.type = type;
  }

  /**
   * Get id
   * @return id
   */
  @javax.annotation.Nonnull
  public String getId() {
    return id;
  }



  /**
   * Get type
   * @return type
   */
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }



  public AddItem201Response labels(@javax.annotation.Nonnull Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public AddItem201Response putLabelsItem(String key, String labelsItem) {
    if (this.labels == null) {
      this.labels = new HashMap<>();
    }
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
   */
  @javax.annotation.Nonnull
  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(@javax.annotation.Nonnull Map<String, String> labels) {
    this.labels = labels;
  }


  public AddItem201Response descriptions(@javax.annotation.Nonnull Map<String, String> descriptions) {
    this.descriptions = descriptions;
    return this;
  }

  public AddItem201Response putDescriptionsItem(String key, String descriptionsItem) {
    if (this.descriptions == null) {
      this.descriptions = new HashMap<>();
    }
    this.descriptions.put(key, descriptionsItem);
    return this;
  }

  /**
   * Get descriptions
   * @return descriptions
   */
  @javax.annotation.Nonnull
  public Map<String, String> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(@javax.annotation.Nonnull Map<String, String> descriptions) {
    this.descriptions = descriptions;
  }


  public AddItem201Response aliases(@javax.annotation.Nonnull Map<String, List<String>> aliases) {
    this.aliases = aliases;
    return this;
  }

  public AddItem201Response putAliasesItem(String key, List<String> aliasesItem) {
    if (this.aliases == null) {
      this.aliases = new HashMap<>();
    }
    this.aliases.put(key, aliasesItem);
    return this;
  }

  /**
   * Get aliases
   * @return aliases
   */
  @javax.annotation.Nonnull
  public Map<String, List<String>> getAliases() {
    return aliases;
  }

  public void setAliases(@javax.annotation.Nonnull Map<String, List<String>> aliases) {
    this.aliases = aliases;
  }


  public AddItem201Response sitelinks(@javax.annotation.Nullable Map<String, Object> sitelinks) {
    this.sitelinks = sitelinks;
    return this;
  }

  /**
   * Get sitelinks
   * @return sitelinks
   */
  @javax.annotation.Nullable
  public Map<String, Object> getSitelinks() {
    return sitelinks;
  }

  public void setSitelinks(@javax.annotation.Nullable Map<String, Object> sitelinks) {
    this.sitelinks = sitelinks;
  }


  public AddItem201Response statements(@javax.annotation.Nullable Map<String, Object> statements) {
    this.statements = statements;
    return this;
  }

  /**
   * Get statements
   * @return statements
   */
  @javax.annotation.Nullable
  public Map<String, Object> getStatements() {
    return statements;
  }

  public void setStatements(@javax.annotation.Nullable Map<String, Object> statements) {
    this.statements = statements;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddItem201Response addItem201Response = (AddItem201Response) o;
    return Objects.equals(this.id, addItem201Response.id) &&
        Objects.equals(this.type, addItem201Response.type) &&
        Objects.equals(this.labels, addItem201Response.labels) &&
        Objects.equals(this.descriptions, addItem201Response.descriptions) &&
        Objects.equals(this.aliases, addItem201Response.aliases) &&
        Objects.equals(this.sitelinks, addItem201Response.sitelinks) &&
        Objects.equals(this.statements, addItem201Response.statements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, labels, descriptions, aliases, sitelinks, statements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddItem201Response {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    descriptions: ").append(toIndentedString(descriptions)).append("\n");
    sb.append("    aliases: ").append(toIndentedString(aliases)).append("\n");
    sb.append("    sitelinks: ").append(toIndentedString(sitelinks)).append("\n");
    sb.append("    statements: ").append(toIndentedString(statements)).append("\n");
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
    openapiFields.add("type");
    openapiFields.add("labels");
    openapiFields.add("descriptions");
    openapiFields.add("aliases");
    openapiFields.add("sitelinks");
    openapiFields.add("statements");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("labels");
    openapiRequiredFields.add("descriptions");
    openapiRequiredFields.add("aliases");
    openapiRequiredFields.add("sitelinks");
    openapiRequiredFields.add("statements");
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to AddItem201Response
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AddItem201Response.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AddItem201Response is not found in the empty JSON string", AddItem201Response.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AddItem201Response.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AddItem201Response` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AddItem201Response.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      // validate the required field `type`
      TypeEnum.validateJsonElement(jsonObj.get("type"));
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AddItem201Response.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AddItem201Response' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AddItem201Response> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AddItem201Response.class));

       return (TypeAdapter<T>) new TypeAdapter<AddItem201Response>() {
           @Override
           public void write(JsonWriter out, AddItem201Response value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AddItem201Response read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of AddItem201Response given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of AddItem201Response
   * @throws IOException if the JSON string is invalid with respect to AddItem201Response
   */
  public static AddItem201Response fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AddItem201Response.class);
  }

  /**
   * Convert an instance of AddItem201Response to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

