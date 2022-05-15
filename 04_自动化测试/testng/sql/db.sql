CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `login_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(125) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;