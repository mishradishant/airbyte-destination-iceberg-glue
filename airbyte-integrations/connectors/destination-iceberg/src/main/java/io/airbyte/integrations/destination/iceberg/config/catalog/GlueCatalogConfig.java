/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.iceberg.config.catalog;

import static io.airbyte.integrations.destination.iceberg.IcebergConstants.GLUE_CATALOG_NAME;
import static io.airbyte.integrations.destination.iceberg.IcebergConstants.DEFAULT_DATABASE_CONFIG_KEY;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.iceberg.CatalogProperties;
import org.apache.iceberg.catalog.Catalog;
import org.apache.iceberg.aws.glue.GlueCatalog;
/**
 * @author Leibniz on 2022/10/26.
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class GlueCatalogConfig extends IcebergCatalogConfig {

  private final String glueDatabaseName;

  public GlueCatalogConfig(JsonNode catalogConfig) {
    String glueDatabaseName = catalogConfig.get(DEFAULT_DATABASE_CONFIG_KEY).asText();
    this.glueDatabaseName = glueDatabaseName;
  }

  @Override
  public Map<String, String> sparkConfigMap() {
    Map<String, String> configMap = new HashMap<>();
    configMap.put("spark.sql.execution.arrow.pyspark.enabled","true");
    configMap.put("iceberg.engine.hive.enabled", "false");
    configMap.put("iceberg.catalog.glue.catalog-impl", "org.apache.iceberg.aws.glue.GlueCatalog");
    configMap.put("iceberg.catalog.glue.lock.table", "myGlueLockTable");
    configMap.put("spark.network.timeout", "300000");
    configMap.put("engine.hive.enabled", "false");
    configMap.put("spark.sql.catalog.glue_catalog.lock.table", "myGlueLockTable");
    configMap.put("spark.network.timeout", "300000");
    configMap.put("spark.sql.defaultCatalog", GLUE_CATALOG_NAME);
    configMap.put("spark.sql.catalog." + GLUE_CATALOG_NAME, "org.apache.iceberg.spark.SparkCatalog");
    configMap.put("spark.sql.catalog." + GLUE_CATALOG_NAME + ".catalog-impl", "org.apache.iceberg.aws.glue.GlueCatalog");
    configMap.put("spark.sql.catalog." + GLUE_CATALOG_NAME + ".io-impl", "org.apache.iceberg.aws.s3.S3FileIO");
    configMap.put("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions");
    configMap.put("spark.driver.extraJavaOptions", "-Dpackaging.type=jar -Djava.io.tmpdir=/tmp");
    configMap.putAll(this.storageConfig.sparkConfigMap(GLUE_CATALOG_NAME));
    return configMap;
  }

  @Override
  public Catalog genCatalog() {
    GlueCatalog catalog = new GlueCatalog();
    Map<String, String> properties = new HashMap<>();
    properties.put(CatalogProperties.WAREHOUSE_LOCATION, this.storageConfig.getWarehouseUri());
    properties.putAll(this.storageConfig.catalogInitializeProperties());
    catalog.initialize(GLUE_CATALOG_NAME, properties);
    return catalog;
  }

}
