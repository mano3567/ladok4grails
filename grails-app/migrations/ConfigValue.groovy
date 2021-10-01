databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110011400") {
        createTable(tableName: "config_value") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ConfigValuePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "name", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "value", type: "VARCHAR(192)") {
                constraints(nullable: "true")
            }

            column(name: "value_type", type: "VARCHAR(192)") {
                constraints(nullable: "false")
            }
        }
    }
}
