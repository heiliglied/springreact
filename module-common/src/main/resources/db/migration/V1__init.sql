CREATE TABLE `users` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` VARCHAR(64) NOT NULL COLLATE 'utf8mb4_unicode_ci' unique,
	`password` VARCHAR(192) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`role` TINYINT(3) UNSIGNED NOT NULL,
	`name` VARCHAR(80) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`email` VARCHAR(80) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`email_verified_at` VARCHAR(192) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`password_change_date` DATE NULL DEFAULT NULL,
	`remember_token` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`created_at` TIMESTAMP NULL DEFAULT NULL,
	`updated_at` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `users_user_id_index` (`user_id`) USING BTREE
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;
