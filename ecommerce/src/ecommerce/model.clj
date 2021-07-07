(ns ecommerce.model)

(defn uuid [] (java.util.UUID/randomUUID))

(defn new-product
  ([name slug price]
   (new-product (uuid) name slug price))
  ([uuid name slug price]
   {:product/id        uuid
    :product/name      name
    :product/slug      slug
    :product/price     price}))

(defn new-category
  ([nome]
   (new-category (uuid) nome))
  ([uuid nome]
   {:categoria/id uuid
    :categoria/nome nome}))