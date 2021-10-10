databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110091000") {
        createTable(tableName: "l3utbildning") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3UtbildningPK")
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

            column(name: "avvecklad", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "benamning", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_en", type: "VARCHAR(255)") {
                constraints(nullable: "true")
            }

            column(name: "benamning_sv", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "class", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "enhet_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "giltig_fran_period_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "har_innehall", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "omfattnings_varde", type: "DECIMAL(6,1)") {
                constraints(nullable: "false")
            }

            column(name: "organisation_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "overliggande_utbildning_uid", type: "VARCHAR(64)") {
                constraints(nullable: "true")
            }

            column(name: "process_status_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "senaste_version", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "studie_ordning_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "utbildnings_form_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_mall_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "utbildning_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "versions_nummer", type: "INTEGER") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110091600") {
        createIndex(indexName: "uid4l3utbilndning_idx", tableName: "l3utbildning") {
            column(name: "uid")
        }

        createIndex(indexName: "edukod4l3utbilndning_idx", tableName: "l3utbildning") {
            column(name: "edu")
            column(name: "utbildnings_kod")
        }
    }
}
