databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110170800") {
        createTable(tableName: "l3utbildnings_tillfalle_period") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3UtbildningsTillfallePeriodPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "DATETIME") {
                constraints(nullable: "true")
            }

            column(name: "last_updated", type: "DATETIME") {
                constraints(nullable: "true")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "forsta_undervisnings_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "omfattnings_varde", type: "decimal(6,1)") {
                constraints(nullable: "false")
            }

            column(name: "period_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "sista_undervisnings_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "utbildnings_tillfalle_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }

        addForeignKeyConstraint(baseColumnNames: "period_id", baseTableName: "l3utbildnings_tillfalle_period", constraintName: "utp2perFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "l3period", referencesUniqueColumn: "true")

        addForeignKeyConstraint(baseColumnNames: "utbildnings_tillfalle_id", baseTableName: "l3utbildnings_tillfalle_period", constraintName: "utp2utbtilFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "l3utbildnings_tillfalle", referencesUniqueColumn: "true")
    }
}
