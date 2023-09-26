/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Geometry;

/**
 *
 * @author Vitor Rinaldini
 */
public class Vector2f {
    public float x, y;
    
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2f copy() {
        return new Vector2f(x, y);
    }
    
    public void normalize() {
        float mod = modulo();
        x = x/mod;
        y = y/mod;
    }
    
    public float modulo() {
        return (float) Math.sqrt(x*x+y*y);
    }
    
    public float produtoInterno(Vector2f other) {
        return x*other.x + y*other.y;
    }
    
    public float angleInRad(Vector2f other) {
        float prod = produtoInterno(other);
        float mod1 = modulo();
        float mod2 = other.modulo();
        double cosAngle = prod/(mod1*mod2);
        return (float) Math.acos(cosAngle);
    }
    
    public Vector2f produtoVetorial() {
        return new Vector2f(y, -x);
    }
    
    public Vector2f sum(Vector2f other) {
        return new Vector2f(x+other.x, y+other.y);
    }
    
    public Vector2f multiply(float number) {
        return new Vector2f(number*x, number*y);
    }
    
}
