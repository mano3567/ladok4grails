databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110111500") {
        createTable(tableName: "l3utbildnings_tillfalle") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3UtbildningsTillfallePK")
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

            column(name: "class", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "installt", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "omfattnings_varde", type: "decimal(6,1)") {
                constraints(nullable: "false")
            }

            column(name: "organisation_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "slut_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "start_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "start_period_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "studie_lokalisering_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "studie_takt_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "undervisnings_form_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "utannonserat", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_instans_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_mall_uid", type: "VARCHAR(64)") {
                constraints(nullable: "true")
            }

            column(name: "utbildnings_tillfalles_kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

        }
    }
}
