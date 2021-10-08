databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110071530") {
        createTable(tableName: "l3student") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3StudentPk")
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

            column(name: "avliden", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "efter_namn", type: "VARCHAR(96)") {
                constraints(nullable: "false")
            }

            column(name: "externt_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "fel_vid_etablering_externt", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "fodelse_data", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "folkbokforings_bevakning_till_och_med", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "for_namn", type: "VARCHAR(96)") {
                constraints(nullable: "false")
            }

            column(name: "kon_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "person_nummer", type: "VARCHAR(16)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "senast_andrad_av", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "senast_sparad", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }
        }

        createIndex(indexName: "ssn_Idx", tableName: "l3student") {
            column(name: "person_nummer")
        }

        createIndex(indexName: "uid_Idx", tableName: "l3student") {
            column(name: "uid")
        }
    }
}
