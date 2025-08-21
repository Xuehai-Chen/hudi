package org.apache.spark.sql.hudi.dml.insert

import org.apache.hadoop.conf.Configuration
import org.apache.hudi.sync.common.HoodieSyncTool
import org.apache.spark.sql.SparkSession

import java.util.Properties

class TestInsertTableWithHiveSupport extends TestInsertTable {

  override lazy val spark: SparkSession = SparkSession.builder()
    .config("spark.sql.warehouse.dir", sparkWareHouse.getCanonicalPath)
    .config("spark.sql.session.timeZone", "UTC")
    .config("hoodie.insert.shuffle.parallelism", "4")
    .config("hoodie.upsert.shuffle.parallelism", "4")
    .config("hoodie.delete.shuffle.parallelism", "4")
    .config("hoodie.datasource.hive_sync.enable", "false")
    .config("hoodie.datasource.meta.sync.enable", "false")
    .config("hoodie.meta.sync.client.tool.class", classOf[DummySyncTool].getName)
    .config(sparkConf())
    .enableHiveSupport()
    .getOrCreate()

}

class DummySyncTool(props: Properties, hadoopConf: Configuration) extends HoodieSyncTool(props, hadoopConf) {
  override def syncHoodieTable(): Unit = {
    // do nothing here
  }
}
