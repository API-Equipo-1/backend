-- SQL Script to create E-Commerce database tables
-- Generated from JPA Entity models
-- Database: MySQL/MariaDB compatible
use ecommerce_db;
-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS detalles_pedido;
DROP TABLE IF EXISTS productos_categorias;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS direcciones;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS usuarios;

-- Table: usuarios
-- Stores user information with authentication details and roles
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    CONSTRAINT chk_role CHECK (role IN ('USER', 'ADMIN'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: categorias
-- Stores product categories
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: productos
-- Stores product information
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    stock INTEGER NOT NULL,
    imagen VARCHAR(500),
    usuario_id BIGINT,
    CONSTRAINT fk_productos_usuario FOREIGN KEY (usuario_id) 
        REFERENCES usuarios(id) ON DELETE SET NULL,
    CONSTRAINT chk_precio CHECK (precio >= 0),
    CONSTRAINT chk_stock CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: productos_categorias
-- Many-to-many relationship between products and categories
CREATE TABLE productos_categorias (
    producto_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, categoria_id),
    CONSTRAINT fk_prod_cat_producto FOREIGN KEY (producto_id) 
        REFERENCES productos(id) ON DELETE CASCADE,
    CONSTRAINT fk_prod_cat_categoria FOREIGN KEY (categoria_id) 
        REFERENCES categorias(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: direcciones
-- Stores delivery addresses for users
CREATE TABLE direcciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(255),
    numero VARCHAR(50),
    localidad VARCHAR(255),
    provincia VARCHAR(255),
    pais VARCHAR(255),
    codigo_postal VARCHAR(20),
    usuario_id BIGINT,
    CONSTRAINT fk_direcciones_usuario FOREIGN KEY (usuario_id) 
        REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: pedidos
-- Stores order information
CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    total DOUBLE NOT NULL,
    usuario_id BIGINT NOT NULL,
    direccion_id BIGINT,
    CONSTRAINT fk_pedidos_usuario FOREIGN KEY (usuario_id) 
        REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_pedidos_direccion FOREIGN KEY (direccion_id) 
        REFERENCES direcciones(id) ON DELETE SET NULL,
    CONSTRAINT chk_total CHECK (total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: detalles_pedido
-- Stores order line items with product details
CREATE TABLE detalles_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    CONSTRAINT fk_detalles_pedido FOREIGN KEY (pedido_id) 
        REFERENCES pedidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalles_producto FOREIGN KEY (producto_id) 
        REFERENCES productos(id) ON DELETE RESTRICT,
    CONSTRAINT chk_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_precio_unitario CHECK (precio_unitario >= 0),
    CONSTRAINT chk_subtotal CHECK (subtotal >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for better query performance
CREATE INDEX idx_productos_usuario ON productos(usuario_id);
CREATE INDEX idx_direcciones_usuario ON direcciones(usuario_id);
CREATE INDEX idx_pedidos_usuario ON pedidos(usuario_id);
CREATE INDEX idx_pedidos_fecha ON pedidos(fecha);
CREATE INDEX idx_detalles_pedido ON detalles_pedido(pedido_id);
CREATE INDEX idx_detalles_producto ON detalles_pedido(producto_id);
CREATE INDEX idx_usuarios_email ON usuarios(email);


-- 🧑 Usuarios
INSERT INTO usuarios (nombre, apellido, email, password, role) VALUES
('Admin', 'System', 'admin@example.com', 'AdminPass123', 'ADMIN'),
('Lionel', 'Messi', 'messi@gmail.com', 'LioMessi22', 'USER'),
('James', 'Hetfield', 'jhetfield@gmail.com', 'papaHet81', 'USER'),
('Max', 'Verstappen', 'maxver@redbull.com', 'superMax33', 'USER'),
('Juan', 'Pérez', 'juanperez@gmail.com', 'JuanPe12', 'USER'),
('John', 'Doe', 'johndoe@email.com', 'johnDoe33', 'USER'),
('Joe', 'Duplantier', 'joe@gmail.com', 'Gojira99', 'USER'),
('Jimi', 'Hendrix', 'jimi@gmail.com', 'Hendrix1', 'USER');

-- 🏠 Direcciones
INSERT INTO direcciones (calle, numero, localidad, provincia, pais, codigo_postal, usuario_id) VALUES
('Av. Siempre Viva', '742', 'Springfield', 'Illinois', 'EE.UU', '62704', 2),
('Calle Metallica', '666', 'Los Angeles', 'California', 'EE.UU', '90001', 3),
('Paddock 1', '33', 'Milton Keynes', 'Buckinghamshire', 'UK', 'MK9', 4),
('Av. Corrientes', '1200', 'Buenos Aires', 'Buenos Aires', 'Argentina', '1000', 5),
('Main Street', '22', 'New York', 'NY', 'EE.UU', '10001', 6);

-- 🏷️ Categorías
INSERT INTO categorias (nombre) VALUES
('Electrónica'),
('Calzado'),
('Accesorios'),
('Indumentaria'),
('Coleccionables'),
('Instrumentos Musicales'),
('Audio Profesional');

-- 📦 Productos
INSERT INTO productos (nombre, descripcion, precio, stock, imagen, usuario_id) VALUES
('Auriculares Bluetooth Sony WH-1000XM4', 'Auriculares inalámbricos premium con cancelación de ruido activa.', 89999.99, 25, 'sony_wh1000xm4.jpg', 1),
('Zapatillas Nike Air Max 270', 'Zapatillas deportivas con tecnología Air Max para máximo confort.', 45999.50, 40, 'nike_airmax270.jpg', 2),
('Apple Watch Series 9', 'Smartwatch con GPS, monitor de salud y pantalla Always-On.', 129999, 15, 'apple_watch9.jpg', 1),
('Campera Patagonia Down Sweater', 'Campera de plumas ultra liviana y compacible.', 67999, 20, 'patagonia_down.jpg', 5),
('Fender Player Stratocaster', 'Guitarra eléctrica de bobina simple con puente sincronizado vintage.', 185999, 8, 'fender_stratocaster.jpg', 7),
('Audio-Technica AT2020 Micrófono', 'Micrófono de condensador cardioide para grabación profesional.', 45999, 9, 'at2020.jpg', 7);

-- 🔗 Productos - Categorías
INSERT INTO productos_categorias (producto_id, categoria_id) VALUES
(1, 1),  -- Electrónica
(2, 2),  -- Calzado
(3, 1),  -- Electrónica
(4, 4),  -- Indumentaria
(5, 6),  -- Instrumentos Musicales
(6, 7);  -- Audio Profesional

-- 🧾 Pedidos
INSERT INTO pedidos (fecha, estado, total, usuario_id, direccion_id) VALUES
('2025-10-01 15:32:00', 'PAGADO', 89999.99, 2, 1),
('2025-10-05 11:12:00', 'ENVIADO', 185999, 7, 5),
('2025-10-07 09:45:00', 'ENTREGADO', 129999, 4, 3);

-- 📋 Detalles de Pedido
INSERT INTO detalles_pedido (pedido_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 89999.99, 89999.99),
(2, 5, 1, 185999, 185999),
(3, 3, 1, 129999, 129999);